package com.presenter.utils;


import com.model.jsondata.login.LoginData;
import com.model.jsondata.store.StoreData;

public class PromptRunnable implements Runnable {
	private boolean v;

	private StoreData objStoreData;
	private LoginData objLoginData;


	public void setValue(boolean inV) {
		this.v = inV;
	}
	public boolean getValue() {

		return this.v;
	}


	public void setStore(StoreData data) {
		this.objStoreData = data;
	}
	public StoreData getStore() {
		return this.objStoreData;
	}

	public void setLogin(LoginData data) {
		this.objLoginData = data;
	}
	public LoginData getLogin() {
		return this.objLoginData;
	}

	public void run() {
		this.run();
	}
}
