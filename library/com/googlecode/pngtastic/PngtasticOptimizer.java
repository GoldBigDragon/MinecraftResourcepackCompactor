package com.googlecode.pngtastic;

import com.googlecode.pngtastic.core.PngException;
import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;

import java.io.IOException;

//https://github.com/depsypher/pngtastic

/**
 * Optimizes PNG images to reduce filesize
 *
 * @see <a href="http://www.w3.org/TR/PNG">PNG spec</a>
 * @see <a href="http://optipng.sourceforge.net/pngtech/">PNG related articles</a>
 * @see <a href="http://www.schaik.com/pngsuite/">PNG reference images</a>
 *
 * @author rayvanderborght
 */
public class PngtasticOptimizer {
	public PngtasticOptimizer(String fileName) {
		Boolean removeGamma = false;
		Integer compressionLevel = 10-((int)src.main.java.io.github.goldbigdragon.resourcepack.compactor.Main.compressPower*10);
		if(compressionLevel > 9)
			compressionLevel = 9;
		else if(compressionLevel < 0)
			compressionLevel = 0;
		
		Integer iterations = 1;
		String compressor = "zopfli";
		String logLevel = "none";
		
		PngOptimizer optimizer = new PngOptimizer(logLevel);
		optimizer.setCompressor(compressor, iterations);

		try {
			PngImage image = new PngImage(fileName, logLevel);
			optimizer.optimize(image, fileName, removeGamma, compressionLevel);

		} catch (PngException | IOException e) {
			e.printStackTrace();
		}
	}

}
