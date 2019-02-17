package com.amazonaws.lambda.demo;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.lambda.model.AmazonLexResponse;
import com.amazonaws.lambda.model.DialogAction;
import com.amazonaws.lambda.model.Message;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LambdaFunctionHandler implements RequestHandler<Object, Object> {

    @Override
    public Object handleRequest(Object input, Context context) {
    	
        context.getLogger().log("Input: " + input);
        Map<String,Object> slotsMap =null;
		Map<String, Object> currentIntentMap=null;
		StringBuilder outputStringBuilder=new StringBuilder("");
		
        String inputContext =input.toString();
        
        ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		String reg= inputContext.replaceAll("[^\\{\\},]+", "\"$0\"");
		String inputContextJSON=reg.replace("\"[\"{", "[{").
				replace("=","\":\"").replace(" ","").
				replace("}\"]\"","}]").
				replace(":\"\"{",":{").
				replace("\"true\"", "true").replace("\"false\"", "false");
		
		System.out.println(inputContextJSON);
		try {
			map = mapper.readValue(inputContextJSON,  new TypeReference<Map<String, Object>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Printing MAP contents");
		
		boolean isVaidInput=false;
		
		if(map!=null){
			System.out.println(map);
			currentIntentMap = (Map<String,Object>)map.get("currentIntent");
			if(currentIntentMap!=null){
				slotsMap = (Map<String, Object>) currentIntentMap.get("slots");
				if(slotsMap!=null){
					System.out.println("LoanAmount:"+slotsMap.get("LoanAmount"));
					System.out.println("PropertyType:"+slotsMap.get("PropertyType"));
					System.out.println("CreditScore:"+slotsMap.get("CreditScore"));
					System.out.println("CoveragePercentage:"+slotsMap.get("CoveragePercentage"));
					System.out.println("PropertyZipCode:"+slotsMap.get("PropertyZipCode"));
					System.out.println("LTV:"+slotsMap.get("LTV"));
					isVaidInput=true;
				}
			}
		}
		
		outputStringBuilder.append(" Rate Quote Generated from ARCH MI : \n");
		if(isVaidInput){
			outputStringBuilder
			.append("LoanAmount: "+slotsMap.get("LoanAmount")+"\n")
			.append("PropertyType: "+slotsMap.get("PropertyType")+"\n")
			.append("CreditScore: "+slotsMap.get("CreditScore")+"\n")
			.append("CoveragePercentage: "+slotsMap.get("CoveragePercentage")+"\n")
			.append("PropertyZipCode: "+slotsMap.get("PropertyZipCode")+"\n")
			.append("LTV: "+slotsMap.get("LTV"));
			
		}
		
        Message message = new Message("PlainText",outputStringBuilder.toString());
		DialogAction dialogAction = new DialogAction("Close", "Fulfilled", message);
		
		return new AmazonLexResponse(dialogAction); 

    }

}
