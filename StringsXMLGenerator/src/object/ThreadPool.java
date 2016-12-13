package object;

import main.HTTPRequestHandler;

public class ThreadPool {
	
	Thread thread;
	String fromWord;
	String resultWord;
	
	HTTPRequestHandler handler;

	public ThreadPool(HTTPRequestHandler handler, String fromLang, String toLang, String fromWord) {
		super();
		thread = new Thread(new Runnable() {			
			@Override
			public void run() {
//				resultWord = handler.getTranslation(fromLang, toLang, fromWord);
				resultWord = Double.toString((Math.random() * 10));
			}
		});
	}
	
	
	public void run() {
		thread.start();
	}


	public String getResultWord() {
		return resultWord;
	}


	public void setResultWord(String resultWord) {
		this.resultWord = resultWord;
	}


	public Thread getThread() {
		return thread;
	}


	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
	public boolean isAlive() {
		return thread.isAlive();
	}
		
}
