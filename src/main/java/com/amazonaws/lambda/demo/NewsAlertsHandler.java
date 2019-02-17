package com.amazonaws.lambda.demo;

import com.amazonaws.lambda.model.AmazonLexResponse;
import com.amazonaws.lambda.model.DialogAction;
import com.amazonaws.lambda.model.Message;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class NewsAlertsHandler implements RequestHandler<Object, Object> {

	@Override
	public Object handleRequest(Object input, Context context) {
		context.getLogger().log("Input: " + input);
		
		String[] newsList={
				"India Won IPL Last Night",
				"TRUMP said CNN is FAKE NEWS",
				"ARCH MI is Mortgage Insurance industry leader"
		};
		
		String news = newsList[(int)(Math.random() * newsList.length)];
		
		Message message = new Message("PlainText", news);
		DialogAction dialogAction = new DialogAction("Close", "Fulfilled", message);

		return new AmazonLexResponse(dialogAction);
	}

}
