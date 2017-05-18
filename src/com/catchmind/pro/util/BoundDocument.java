package com.catchmind.pro.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

public class BoundDocument extends PlainDocument {
	protected int charLimit;
	protected JTextComponent textComp;

	public BoundDocument(int charLimit) {
		this.charLimit = charLimit;
	}

	public BoundDocument(int charLimit, JTextComponent textComp) {
		this.charLimit = charLimit;
		this.textComp = textComp;
	}

	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if (textComp.getText().getBytes().length + str.getBytes().length <= charLimit) {
			super.insertString(offs, str, a);
		}
	}
}
