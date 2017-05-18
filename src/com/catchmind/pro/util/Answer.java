package com.catchmind.pro.util;

import java.util.List;
import java.util.Random;

import com.catchmind.pro.dao.AnswerDAO;

public class Answer {
	public static List<String> answerList = null;
	
	public static String getAnswer(){
		if(answerList == null){
			answerList = AnswerDAO.getAnswerDAO().getaAnswer();
		}
		Random random = new Random();
		return answerList.get(random.nextInt(answerList.size()));
	}
}
