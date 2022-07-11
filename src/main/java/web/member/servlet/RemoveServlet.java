package web.member.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.member.service.MemberService;
import web.member.service.impl.MemberServiceImpl;
import web.member.vo.Member;

@WebServlet("/member/remove")
public class RemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		JsonObject respObject = new JsonObject();
		Member member = gson.fromJson(request.getReader(), Member.class);
		try {
			MemberService service = new MemberServiceImpl();
			Integer status = service.remove(member);
			if (status > 0) {
				respObject.addProperty("msg", "success");
			} else {
				respObject.addProperty("msg", "fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().append(gson.toJson(respObject));
	}

}
