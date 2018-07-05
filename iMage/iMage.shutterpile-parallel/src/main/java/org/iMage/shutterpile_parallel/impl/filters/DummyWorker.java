package org.iMage.shutterpile_parallel.impl.filters;

public class DummyWorker implements Runnable {

	public DummyWorker() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		System.out.println("runner");
	}

}
