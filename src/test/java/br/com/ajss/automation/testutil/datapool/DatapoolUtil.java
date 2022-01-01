package br.com.ajss.automation.testutil.datapool;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

public class DatapoolUtil {
	static Agencia obj;
	
	public static Agencia InformacoesAgencia() {
		XStream xstream = new XStream();
		//xstream.autodetectAnnotations(true);
		xstream.alias("datapool", Datapool.class);
		xstream.alias("agencia", Agencia.class);
		xstream.alias("operacao", Operacao.class);
		xstream.processAnnotations(Datapool.class);
		//xstream.processAnnotations(Agencia.class);
		//xstream.processAnnotations(Operacao.class);
		xstream.addImplicitCollection(Datapool.class,"agencia",Agencia.class);
		xstream.addImplicitCollection(Agencia.class,"operacao",Operacao.class);
		
		// allow some basics
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypeHierarchy(Collection.class);
		
		String filename = System.getProperty("user.dir")+"\\src\\test\\resources\\datapool\\Datapool.xml";
		File file = new File(filename);
		
		Datapool datapool = (Datapool) xstream.fromXML(file);
		ArrayList<Agencia> dados = datapool.getAgencia();
		
		int n = dados.size();
		for (int i=0; i<n; i++) {
			if (dados.get(i).getNumAgencia().equals(System.getProperty("agencia"))){
				System.out.println("Agencia informada: " + dados.get(i).getNumAgencia());
				obj = dados.get(i);
			} else if ((System.getProperty("agencia") == null) && (dados.get(i).getNumAgencia().equals("3965"))){
				obj = dados.get(i);
			}
		}
		
		return obj;
	}
}

