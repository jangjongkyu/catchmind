package com.catchmind.pro.util;

import java.util.Observable;

public class WindowMovingObserver extends Observable{
	
	public void action() {
		setChanged();
		notifyObservers();
	}
	
}
