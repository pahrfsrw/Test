package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import generation.Generation;
import generation.Individual;

public class IO {

	private static final String exportPath = "./export/";
	
	public static <T extends Individual<T>> void writeIndividual(String fileName, Individual<T> indiv){
		File file;
		FileOutputStream fos;
		ObjectOutputStream oos;
		
		
		file = new File(exportPath + fileName);
		try{
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(indiv);
			fos.close();
			oos.close();
		} catch(IOException e){
			e.printStackTrace();
			return;
		}
	}
	
	public static <T extends Individual<T>> void writeGeneration(String fileName, Generation<T> gen){
		File file;
		FileOutputStream fos;
		ObjectOutputStream oos;
		
		file = new File(exportPath + fileName);
		try{
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(gen);
			oos.close();
			fos.close();
		} catch(IOException e){
			e.printStackTrace();
			return;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Individual<T>> Individual<T> readIndividual(String fileName){
		File file = new File(exportPath + fileName);
		FileInputStream fis;
		ObjectInputStream ois;
		Individual<T> indiv = null;
		
		try{
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			indiv = (Individual<T>) ois.readObject();
			ois.close();
			fis.close();
		} catch(IOException|ClassNotFoundException e){
			e.printStackTrace();
		}
		return indiv;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Individual<T>> Generation<T> readGeneration(String fileName){
		File file = new File(exportPath + fileName);
		FileInputStream fis;
		ObjectInputStream ois;
		Generation<T> gen = null;
		
		try{
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			gen = (Generation<T>) ois.readObject();
			ois.close();
			fis.close();
			System.out.println(gen);
		} catch(IOException|ClassNotFoundException e){
			e.printStackTrace();
		}
		return gen;
	}
	
	public static void main(String[] args){
		//IO.writeIndividual("test.txt", null);
		IO.readIndividual("test.txt");
	}
}
