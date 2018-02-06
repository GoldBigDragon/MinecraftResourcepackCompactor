package src.main.java.io.github.goldbigdragon.resourcepack.compactor;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Copyright 2018 GoldBigDragon (https://github.com/GoldBigDragon)
 * 
 * GoldBigDragonRPG is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License.

 * This is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class Main {

	public static boolean started = false;
	
	public static String path="./";
	public static boolean searchInnerDir = true;
	public static float compressPower = 1f;

	public static List<String> jsonFilePath = new ArrayList<>();
	public static List<String> imageFilePath = new ArrayList<>();

	public static ArrayList<Compressor> threads = new ArrayList<>();

	public static int totalSize = 0;
	private static BigInteger originalSize = new BigInteger("0"); 
	public static void main(String[] args)
	{
		String temp = null;
		Scanner scanner = new Scanner(System.in);
		char language = 'E';
		while(true)
		{
			System.out.println("[Select Langauage!]\n 1 = English\n 2 = 한국어\n 3 = 日本語\n 4 = 中国的\n 5 = русский");
			System.out.print(" ▶ ");
			temp = scanner.nextLine();
			if(temp.equals("1") || temp.equals("2") || temp.equals("3") || temp.equals("4") || temp.equals("5"))
			{
				if(temp.equals("1"))
					language = 'E';
				else if(temp.equals("2"))
					language = 'K';
				else if(temp.equals("3"))
					language = 'J';
				else if(temp.equals("4"))
					language = 'C';
				else if(temp.equals("5"))
					language = 'R';
				break;
			}
		}
		if(language=='E')
			System.out.println("[Type resource-pack directory path!]");
		else if(language=='K')
			System.out.println("[리소스팩 경로를 입력 하세요!]");
		else if(language=='J')
			System.out.println("[リソースパックのディレクトリパスを入力します!]");
		else if(language=='C')
			System.out.println("[键入资源包目录路径!]");
		else if(language=='R')
			System.out.println("[Введите путь каталога resource-pack!]");
		System.out.print(" ▶ ");
		path = scanner.nextLine();
		
		boolean compressText = false;
		boolean compressImage = false;
		int threadAmount = 1;
		
		while(true)
		{
			if(language=='E')
				System.out.println("[Select compression mode!]\n 1 = Only text file\n 2 = Only image file\n 3 = All of things (json, mcmeta, png, jpg, jpeg)");
			else if(language=='K')
				System.out.println("[압축 방식을 선택하세요!]\n 1 = 텍스트 파일만\n 2 = 그림 파일만\n 3 = 모든 파일 (json, mcmeta, png, jpg, jpeg)");
			else if(language=='J')
				System.out.println("[圧縮モードを選択する!]\n 1 = テキストファイルのみ\n 2 = 画像ファイルのみ\n 3 = すべてのもの (json, mcmeta, png, jpg, jpeg)");
			else if(language=='C')
				System.out.println("[选择压缩模式!]\n 1 = 只有文本文件\n 2 = 只有图像文件\n 3 = 所有的东西 (json, mcmeta, png, jpg, jpeg)");
			else if(language=='R')
				System.out.println("[Выберите режим сжатия!]\n 1 = Только текстовый файл\n 2 = Только файл изображения\n 3 = Все (json, mcmeta, png, jpg, jpeg)");
			System.out.print(" ▶ ");
			temp = scanner.nextLine();
			if(temp.equals("1") || temp.equals("2") || temp.equals("3"))
				break;
		}
		if(temp.equals("1")||temp.equals("3"))
			compressText = true;
		if(temp.equals("2")||temp.equals("3"))
			compressImage = true;
		
		if(temp.equals("2")||temp.equals("3"))
		{
			while(true)
			{
				if(language=='E')
					System.out.println("[Select image compression power!]\n 1 = Maintain original as much as possible\n 2 = Rare\n 3 = Medium\n 4 = Well done\n 5 = I am Gordon Ramsay");
				else if(language=='K')
					System.out.println("[이미지 파일의 압축 강도를 선택 하세요!]\n 1 = 원본을 최대한 유지하며\n 2 = 살짝 익혀 주세요\n 3 = 적당히 익혀 주세요\n 4 = 잘 익혀서 주세요\n 5 = 내가 바로 고든 램지");
				else if(language=='J')
					System.out.println("[画像の圧縮力を選択する!]\n 1 = 可能な限りオリジナルを維持する\n 2 = レア\n 3 = ミディアム\n 4 = よくできた\n 5 = 私はゴードン・ラムゼイです");
				else if(language=='C')
					System.out.println("[选择图像压缩功率!]\n 1 = 尽可能保持原创\n 2 = 罕见\n 3 = 中等\n 4 = 干得好\n 5 = 我是戈登拉姆齐");
				else if(language=='R')
					System.out.println("[Выберите мощность сжатия изображения!]\n 1 = Поддерживайте оригинал как можно больше\n 2 = Редкий\n 3 = Средний\n 4 = Молодцы\n 5 = Я Гордон Рамсей");
				System.out.print(" ▶ ");
				temp = scanner.nextLine();
				if(temp.equals("1") || temp.equals("2") || temp.equals("3") || temp.equals("4") || temp.equals("5"))
					break;
			}
		}
		if(temp.equals("5"))
		{
			while(true)
			{
				if(language=='E')
					System.out.println("[Type compression power!]\n [Min] 0 ~ 1000 [Max]");
				else if(language=='K')
					System.out.println("[압축 강도를 입력 해 주세요!]\n [최소] 0 ~ 1000 [최대]");
				else if(language=='J')
					System.out.println("[圧縮力を入力!]\n [最小] 0 ~ 1000 [最大]");
				else if(language=='C') 
					System.out.println("[输入压缩功率!]\n [最小] 0 ~ 1000 [最大]");
				else if(language=='R')
					System.out.println("[Введите мощность сжатия!]\n [Мин] 0 ~ 1000 [Макс]");
				System.out.print(" ▶ ");
				temp = scanner.nextLine();
				try {
					int parsedInt = Integer.parseInt(temp);
					if (parsedInt >= 0 && parsedInt <= 1000)
					{
						compressPower = 1.0f - (parsedInt*0.001f);
						break;
					}
					else
					{
						if(language=='E')
							System.out.println("[Type compression power!]\n [Min] 0 ~ 1000 [Max]");
						else if(language=='K')
							System.out.println("[압축 강도를 입력 해 주세요!]\n [최소] 0 ~ 1000 [최대]");
						else if(language=='J')
							System.out.println("[圧縮力を入力!]\n [最小] 0 ~ 1000 [最大]");
						else if(language=='C') 
							System.out.println("[输入压缩功率!]\n [最小] 0 ~ 1000 [最大]");
						else if(language=='R')
							System.out.println("[Введите мощность сжатия!]\n [Мин] 0 ~ 1000 [Макс]");
						System.out.print(" ▶ ");
					}
				} catch (NumberFormatException e) {
					if(language=='E')
						System.out.println("[Type compression power!]\n [Min] 0 ~ 1000 [Max]");
					else if(language=='K')
						System.out.println("[압축 강도를 입력 해 주세요!]\n [최소] 0 ~ 1000 [최대]");
					else if(language=='J')
						System.out.println("[圧縮力を入力!]\n [最小] 0 ~ 1000 [最大]");
					else if(language=='C') 
						System.out.println("[输入压缩功率!]\n [最小] 0 ~ 1000 [最大]");
					else if(language=='R')
						System.out.println("[Введите мощность сжатия!]\n [Мин] 0 ~ 1000 [Макс]");
					System.out.print(" ▶ ");
				}
				
			}
		}
		else
		{
			if(temp.equals("1"))
				compressPower = 0.9F;
			else if(temp.equals("2"))
				compressPower = 0.7F;
			else if(temp.equals("3"))
				compressPower = 0.5F;
			else if(temp.equals("4"))
				compressPower = 0.3F;
		}
		while(true)
		{
			if(language=='E')
				System.out.println("[Type threads amount!]\n [Min] 1 ~ 1000 [Max]");
			else if(language=='K')
				System.out.println("[스레드 개수를 입력 해 주세요!]\n [최소] 1 ~ 1000 [최대]");
			else if(language=='J')
				System.out.println("[スレッド数を入力して!]\n [最小] 0 ~ 1000 [最大]");
			else if(language=='C') 
				System.out.println("[输入线程数量!]\n [最小] 0 ~ 1000 [最大]");
			else if(language=='R')
				System.out.println("[Введите сумму резьбы!]\n [Мин] 0 ~ 1000 [Макс]");
			System.out.print(" ▶ ");
			temp = scanner.nextLine();
			try {
				int parsedInt = Integer.parseInt(temp);
				if (parsedInt >= 1 && parsedInt <= 1000)
				{
					threadAmount = parsedInt;
					break;
				}
				else
				{
					if(language=='E')
						System.out.println("[Type threads amount!]\n [Min] 1 ~ 1000 [Max]");
					else if(language=='K')
						System.out.println("[스레드 개수를 입력 해 주세요!]\n [Min] 1 ~ 1000 [Max]");
					else if(language=='J')
						System.out.println("[スレッド数を入力して!]\n [最小] 0 ~ 1000 [最大]");
					else if(language=='C') 
						System.out.println("[输入线程数量!]\n [最小] 0 ~ 1000 [最大]");
					else if(language=='R')
						System.out.println("[Введите сумму резьбы!]\n [Мин] 0 ~ 1000 [Макс]");
					System.out.print(" ▶ ");
				}
			} catch (NumberFormatException e) {
				if(language=='E')
					System.out.println("[Type threads amount!]\n [Min] 1 ~ 1000 [Max]");
				else if(language=='K')
					System.out.println("[스레드 개수를 입력 해 주세요!]\n [Min] 1 ~ 1000 [Max]");
				else if(language=='J')
					System.out.println("[スレッド数を入力して!]\n [最小] 0 ~ 1000 [最大]");
				else if(language=='C') 
					System.out.println("[输入线程数量!]\n [最小] 0 ~ 1000 [最大]");
				else if(language=='R')
					System.out.println("[Введите сумму резьбы!]\n [Мин] 0 ~ 1000 [Макс]");
				System.out.print(" ▶ ");
			}
		}
		jsonFilePath.clear();
		imageFilePath.clear();
		
		System.out.println("\n\nPath\t: "+ path);
		if(compressText && compressImage)
			System.out.println("Compress Target : Minecraft modelling(Cubik Pro) JSON, MCMETA, PNG, JPG, JPEG");
		else if(compressText)
			System.out.println("Compress Target : Minecraft modelling(Cubik Pro) JSON,MCMETA");
		else if(compressImage)
			System.out.println("Compress Target : PNG, JPG, JPEG");
		if(compressImage)
			System.out.println("Compress Power : " + compressPower);
		System.out.println("[START]");
		
		getFilePath(new File(path));
		totalSize = jsonFilePath.size()+imageFilePath.size();

		originalSize = folderSize(new File(path));

		started = true;
		threads.clear();
		for(int count = 0; count < threadAmount; count++)
			threads.add(new Compressor(count));
		for(int count = 0; count < threadAmount; count++)
			threads.get(count).start();
	}
	
	public static void getFilePath(File dir) {
		File[] fList = dir.listFiles();
		for (File f : fList) {
			if (searchInnerDir && f.isDirectory())
				getFilePath(new File(f.getAbsolutePath()));
			else if (f.isFile())
			{
				if(f.getName().endsWith(".json") || f.getName().endsWith(".mcmeta"))
					jsonFilePath.add(f.getAbsolutePath());
				else if(f.getName().endsWith(".png") || f.getName().endsWith(".jpg") || f.getName().endsWith(".jpeg"))
					imageFilePath.add(f.getAbsolutePath());
			}
		}
	}
	
	public static String getConvertedTextureName(int order)
	{
		String amount = Integer.toString(order / 26);
		order %= 26;
		if(amount.equals("0"))
			return Character.toString((char)(order + 97));
		else
			return Character.toString((char)(order + 97))+amount;
	}
	
	public static BigInteger folderSize(File directory) {
		BigInteger bi = new BigInteger("0");
	    for (File file : directory.listFiles())
	    {
	        if (file.isFile())
	        	bi = bi.add(BigInteger.valueOf(file.length()));
	        else
	        	bi = bi.add(folderSize(file));
	    }
	    return bi;
	}
	
	public static void sendResult()
	{
		BigInteger afterSize = folderSize(new File(path));
		System.out.println("[END]");
		System.out.println("Edited Files\t: "+totalSize);
		
        int originalGb = 0;
        int originalMb = 0;
        int originalKb = 0;
        
        int afterGb = 0;
        int afterMb = 0;
        int afterKb = 0;
        //GB
        if(originalSize.compareTo(BigInteger.valueOf(1073741824)) > -1)
        {
        	originalGb = originalSize.divide(BigInteger.valueOf(1073741824)).intValue();
        	originalSize = originalSize.subtract(BigInteger.valueOf(originalGb*1073741824));
        }
        //MB
        if(originalSize.compareTo(BigInteger.valueOf(1048576)) > -1)
        {
        	originalMb = originalSize.divide(BigInteger.valueOf(1048576)).intValue();
        	originalSize = originalSize.subtract(BigInteger.valueOf(originalMb*1048576));
        }
        //KB
        if(originalSize.compareTo(BigInteger.valueOf(1024)) > -1)
        {
        	originalKb = originalSize.divide(BigInteger.valueOf(1024)).intValue();
        	originalSize = originalSize.subtract(BigInteger.valueOf(originalKb*1024));
        }
        
        //GB
        if(afterSize.compareTo(BigInteger.valueOf(1073741824)) > -1)
        {
        	afterGb = afterSize.divide(BigInteger.valueOf(1073741824)).intValue();
        	afterSize = afterSize.subtract(BigInteger.valueOf(afterGb*1073741824));
        }
        //MB
        if(afterSize.compareTo(BigInteger.valueOf(1048576)) > -1)
        {
        	afterMb = afterSize.divide(BigInteger.valueOf(1048576)).intValue();
        	afterSize = afterSize.subtract(BigInteger.valueOf(afterMb*1048576));
        }
        //KB
        if(afterSize.compareTo(BigInteger.valueOf(1024)) > -1)
        {
        	afterKb = afterSize.divide(BigInteger.valueOf(1024)).intValue();
        	afterSize = afterSize.subtract(BigInteger.valueOf(afterKb*1024));
        }
        
        StringBuilder sb = new StringBuilder();
        if(originalGb > 0)
        	sb.append(originalGb+"GB ");
        if(originalMb > 0)
        	sb.append(originalMb+"MB ");
        if(originalKb > 0)
        	sb.append(originalKb+"KB ");
    	sb.append(originalSize.toString());
    	sb.append("Byte [Before]");
		System.out.println(sb.toString());
		
		sb = new StringBuilder();
        if(afterGb > 0)
        	sb.append(afterGb+"GB ");
        if(afterMb > 0)
        	sb.append(afterMb+"MB ");
        if(afterKb > 0)
        	sb.append(afterKb+"KB ");
    	sb.append(afterSize.toString());
    	sb.append("Byte [After]");
		System.out.println(sb.toString());
		
		sb = new StringBuilder();
        if(originalGb - afterGb > 0)
        	sb.append(originalGb - afterGb+"GB ");
        if(originalMb - afterMb > 0)
        	sb.append(originalMb - afterMb+"MB ");
        if(originalKb - afterKb > 0)
        	sb.append(originalKb - afterKb+"KB ");
    	sb.append(originalSize.subtract(afterSize).toString());
    	sb.append("Byte [Saved]");
		System.out.println(sb.toString());
	}

}