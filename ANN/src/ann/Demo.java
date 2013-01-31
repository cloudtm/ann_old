package ann;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.*;

public class Demo {
	
	private Nns nn;
	private int minReplication;
	private int maxNodeNumber;
	
	
	public Demo(Nns nn, int a, int b){
		this.nn = nn;
		this.minReplication = a;
		this.maxNodeNumber = b;
	}
	
	public void ExecuteDemo(String fileInput, String fileOutput){
		BufferedReader br;
		FileInputStream fis;
		InputStreamReader isr;
		FileOutputStream fos;
		PrintStream ps;
		String stringa;
		OptimalPrevision op;
		int numeroClient;
		try{
			File f = new File(fileInput);
			fis = new FileInputStream(f);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			File output = new File(fileOutput);
			fos = new FileOutputStream(output);
			ps = new PrintStream(fos);
			stringa = br.readLine();
			while(stringa !=null ){
				numeroClient = Integer.parseInt(stringa);
				op = this.nn.getOptimalPrevision(minReplication, maxNodeNumber, numeroClient);
				ps.println(numeroClient + ", " + op.getServerThroughput() + ", " + op.getReplicationThroughput()
							+ ", " + op.getServerResponseTime() + ", " + op.getReplicationResponseTime());
			}
			ps.close();
			fos.close();
			br.close();
			isr.close();
			fis.close();
		}catch(Exception e){
			
		}
	}
}
