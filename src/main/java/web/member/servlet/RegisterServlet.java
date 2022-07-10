package web.member.servlet;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import web.member.service.MemberService;
import web.member.service.impl.MemberServiceImpl;
import web.member.vo.Member;

@WebServlet("/member/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		// GSON parser to SQL data format, referenced by: 
		// https://stackoverflow.com/questions/32610476/deserializing-with-gson-converting-java-sql-date-incorrectly
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonObject respObject = new JsonObject();
		
		Member member = gson.fromJson(request.getReader(), Member.class);
		try {
			MemberService service = new MemberServiceImpl();
			Integer status = service.register(member);
			if (status > 0) {
				respObject.addProperty("msg", "success");
			} else {
				respObject.addProperty("msg", "fail");
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println(gson.toJson(respObject));
		response.getWriter().append(gson.toJson(respObject));

 		
	}

}
