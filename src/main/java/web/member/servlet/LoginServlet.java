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

@WebServlet("/member/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
//		setHeaders(response);
		Gson gson = new Gson();
		JsonObject respObject = new JsonObject();

		Member member = gson.fromJson(request.getReader(), Member.class);
		try {
			MemberService service = new MemberServiceImpl();
			member = service.login(member.getMemID(), member.getMemPassword());
			if (member != null) {
				respObject.addProperty("msg", "success");
				// Referenced from https://stackoverflow.com/questions/22585970/how-to-add-an-object-as-a-property-of-a-jsonobject-object
				respObject.add("member", new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJsonTree(member));
			} else {
				respObject.addProperty("msg", "fail");
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println(gson.toJson(respObject));
		response.getWriter().append(gson.toJson(respObject));

	}
	/*
	 * 誇域
	 */
//
//	@Override
//	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		setHeaders(resp);
//	}
//	private void setHeaders(HttpServletResponse response) {
//		// 重要
//		response.setContentType("application/json;charset=UTF-8");
//		response.setHeader("Cache-control", "no-cache, no-store");
//		response.setHeader("Pragma", "no-cache");
//		response.setHeader("Expires", "-1");
//
//		// 重要
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		response.addHeader("Access-Control-Allow-Methods", "*");
//		response.addHeader("Access-Control-Allow-Headers", "*");
//		response.addHeader("Access-Control-Max-Age", "86400");
//	}
}
