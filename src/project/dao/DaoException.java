/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.dao;

/**
 *
 * @author renato
 */
public class DaoException extends Exception {
	private String msg;
	public DaoException(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
}
