package src.main.java.io.github.goldbigdragon.resourcepack.compactor;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

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

public class Compressor extends Thread
{
	public Compressor(int count)
	{
		this.setName("[Thread"+count+"] Created");
	}
	
	@Override
	public void run()
	{
        try
        {
            BufferedReader br = null;
            BufferedWriter bw = null;
            File file = null;
            StringBuilder sb;
            StringBuilder originalSb;
            String line;
            String[] textures;
            boolean copyrightIndication = false;
            int textureNumber;
            ArrayList<String> originalTextureName;
            ArrayList<String> tinifyTextureName;
            

        	OutputStream out = null;
        	ImageWriter writer = null;
        	File input;
        	File output;
        	BufferedImage image;
        	ImageOutputStream ios;
        	ImageWriteParam param;

	        int remainning = 0;
            long start;
            String fileName = null;
            
			while(Main.started)
			{
				if(! Main.jsonFilePath.isEmpty())
				{
					fileName = Main.jsonFilePath.get(0);
					Main.jsonFilePath.remove(fileName);
	        		start = System.currentTimeMillis();
	            	textureNumber = 0;
	            	copyrightIndication = false;
	            	sb = new StringBuilder();
	            	originalSb = new StringBuilder();
	            	file = new File(fileName);
                    br = new BufferedReader(new FileReader(file));
                    while ((line = br.readLine()) != null)
                    {
                    	originalSb.append(line);
                    	originalSb.append("\n");
                    	
                    	//First encountered "comment" is regard it as author field.
                		if(line.contains("comment"))
                		{
	                    	if( ! copyrightIndication)
	                    	{
	                    		copyrightIndication = true;
	                        	sb.append(line.trim());
	                    	}
	                    	else
	                    		continue;
                		}
                		//Delete useless tab, spacing, useless decimal places or less.
                		else
                			sb.append(line.trim().replaceAll(" ", "").replaceAll("3333", "33").replaceAll("6666", "66").replaceAll("9999", "99").replaceAll("667", "67"));
                    	sb.append("\n");
                    }
    	            br.close();
    	            line = sb.toString();
    	            //if file is modelling json
    	            if(line.contains("elements") && line.contains("textures"))
    	            {
    	                originalTextureName = new ArrayList<>();
    	                tinifyTextureName = new ArrayList<>();
        	            textures = line.split("\"elements\":")[0].split("\"textures\":")[1].split("\n");
        	            for(int count2 = 0; count2 < textures.length; count2++)
        	            {
        	            	if(textures[count2].contains(":"))
        	            	{
        	            		line = textures[count2].split(":")[0];
        	            		originalTextureName.add(line.substring(1, line.lastIndexOf("\"")));
        	            		tinifyTextureName.add(getConvertedTextureName(textureNumber));
        	            		textureNumber++;
        	            	}
        	            }
        	            line = sb.toString();
        	            for(int count2 = 0; count2 < originalTextureName.size(); count2++)
        	            {
        	            	if(line.contains("#"+originalTextureName.get(count2)))
        	            	{
            	            	line = line.replaceAll("\n\""+originalTextureName.get(count2)+"\":\"", "\n\""+tinifyTextureName.get(count2)+"\":\"");
            	            	line = line.replaceAll("\"texture\":\"#"+originalTextureName.get(count2), "\"texture\":\"#"+tinifyTextureName.get(count2));
        	            	}
        	            	else//delete no used texture links
        	            	{
        	            		Pattern p = Pattern.compile("\""+originalTextureName.get(count2)+"\":(.*?)\n",  Pattern.CASE_INSENSITIVE);
        	            		Matcher m = p.matcher(line);
        	            		while (m.find())
        	            		{
        	            			String texturePath = m.group(1); 
                	            	line = line.replaceAll("\""+originalTextureName.get(count2)+"\":"+texturePath+"\n", "");
        	            			break;
        	            		}
        	            	}
        	            }
    	            }
                    bw = new BufferedWriter(new FileWriter(file));
                    bw.write(line);
                    bw.flush();
                    bw.close();
				}
				else if( ! Main.imageFilePath.isEmpty())
				{
	        		start = System.currentTimeMillis();
					fileName = Main.imageFilePath.get(0);
					Main.imageFilePath.remove(fileName);
	                input = new File(fileName);
	                if(fileName.endsWith(".png"))
	                {
	                	new com.googlecode.pngtastic.PngtasticOptimizer(fileName);
	    				remainning =  Main.totalSize-(Main.jsonFilePath.size()+Main.imageFilePath.size());
	            		System.out.println("[" +remainning + " / " + Main.totalSize + "]"+fileName +" : "+(System.currentTimeMillis() - start)+"ms");
	                	continue;
	                }
	                image = ImageIO.read(input);

	                output = new File(fileName);
	                out = new FileOutputStream(output);

	                if(fileName.endsWith(".jpg"))
	                	writer = ImageIO.getImageWritersByFormatName("jpg").next();
	                else if(fileName.endsWith(".jpeg"))
	                	writer = ImageIO.getImageWritersByFormatName("jpeg").next();
	                ios = ImageIO.createImageOutputStream(out);
	                writer.setOutput(ios);
	                param = writer.getDefaultWriteParam();
	                if (param.canWriteCompressed()){
	                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	                    param.setCompressionQuality(Main.compressPower);
	                }
	                writer.write(null, new IIOImage(image, null, null), param);
	                out.close();
	                ios.close();
	                writer.dispose();
				}
				else
					break;
				remainning =  Main.totalSize-(Main.jsonFilePath.size()+Main.imageFilePath.size());
        		System.out.println("[" +remainning + " / " + Main.totalSize + "]"+fileName +" : "+(System.currentTimeMillis() - start)+"ms");
			}
        }
        catch (IOException | NullPointerException e)
        {
            e.printStackTrace();
        }
		System.out.println("[Thread"+this.getName()+"] Finished work");
		if(Main.threads.size() == 1)
			Main.sendResult();
		Main.threads.remove(this);
		return;
	}
	
	public String getConvertedTextureName(int order)
	{
		String amount = Integer.toString(order / 26);
		order %= 26;
		if(amount.equals("0"))
			return Character.toString((char)(order + 97));
		else
			return Character.toString((char)(order + 97))+amount;
	}
}
