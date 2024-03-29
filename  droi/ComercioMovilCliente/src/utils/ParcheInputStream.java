package utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ParcheInputStream extends FilterInputStream{
	public ParcheInputStream(InputStream in){
		super(in);
	}
	
	public long skip(long n) throws IOException {
	    long m = 0L;
	    while (m < n) {
	      long _m = in.skip(n-m);
	      if (_m == 0L) break;
	      m += _m;
	    }
	    return m;
	  }
	

}
