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

/**
 * Servlet implementation class ModifyServlet
 */
@WebServlet("/member/modify")
public class ModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonObject respObject = new JsonObject();
		Member member = gson.fromJson(request.getReader(), Member.class);
		try {
			MemberService service = new MemberServiceImpl();
			if (service.modify(member) > 0) {
				respObject.addProperty("msg", "success");
				// Referenced from https://stackoverflow.com/questions/22585970/how-to-add-an-object-as-a-property-of-a-jsonobject-object
				// respObject.add("member", new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJsonTree(member));
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
