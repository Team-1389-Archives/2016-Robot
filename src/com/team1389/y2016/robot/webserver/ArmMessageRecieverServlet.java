package com.team1389.y2016.robot.webserver;

import com.team1389.base.webserver.JSONPostServlet;

public class ArmMessageRecieverServlet extends JSONPostServlet<ArmMessageRecieverServlet.Message, ArmMessageRecieverServlet.NoResponse>{
	public class Message{
		public String msg;
	}
	public class NoResponse{}

	@Override
	public NoResponse onPost(Message fromClient) {
		System.out.println(fromClient.msg);
		
		return new NoResponse();
	}


	@Override
	public Class<Message> whatClassIsFromClient() {
		return ArmMessageRecieverServlet.Message.class;
	}

}
