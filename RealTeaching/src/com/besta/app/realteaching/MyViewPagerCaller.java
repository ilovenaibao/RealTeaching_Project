package com.besta.app.realteaching;

public class MyViewPagerCaller {

	public interface MyViewPagerCallerInterface {

	}

	public class Caller {
		private MyViewPagerCallerInterface mc;

		public Caller() {

		}
	}

	public class Callee implements MyViewPagerCallerInterface {

	}
}
