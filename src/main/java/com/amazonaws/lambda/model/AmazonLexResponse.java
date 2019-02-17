package com.amazonaws.lambda.model;

public class AmazonLexResponse {
	
	private DialogAction dialogAction;

	public AmazonLexResponse(DialogAction dialogAction) {
		super();
		this.dialogAction = dialogAction;
	}

	public DialogAction getDialogAction() {
		return dialogAction;
	}

	public void setDialogAction(DialogAction dialogAction) {
		this.dialogAction = dialogAction;
	}

	@Override
	public String toString() {
		return "AmazonLexResponse [dialogAction=" + dialogAction + "]";
	}
	

}
