package Graphic;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class AddComposite implements Composite
{
  public static AddComposite instance = new AddComposite();

  private AddComposite(){}

  @Override
  public CompositeContext createContext(ColorModel srcColorModel,ColorModel dstColorModel,RenderingHints hints)
  {
    return new AddCompositeContext();
  }

  //
  public static class AddCompositeContext implements CompositeContext
  {
    public AddCompositeContext(){}

    @Override
    public void compose(Raster src,Raster dstIn,WritableRaster dstOut)
    {
      Rectangle srcRect    = src.getBounds();
      Rectangle dstInRect  = dstIn.getBounds();
      Rectangle dstOutRect = dstOut.getBounds();
      
      int       x,y;
      int       w     = Math.min(Math.min(srcRect.width,dstOutRect.width),dstInRect.width);
      int       h     = Math.min(Math.min(srcRect.height,dstOutRect.height),dstInRect.height);
      
      int       minCh = Math.min(src.getNumBands(),dstIn.getNumBands());
      
      int[]     pxSrc = null;
      int[]     pxDst = null;
      int       alpha;

      for(y = 0; y < h; y++)
      {
        for(x = 0; x < w; x++)
        {
          pxSrc = src.getPixel(x + srcRect.x,y + srcRect.y,pxSrc);
          pxDst = dstIn.getPixel(x + dstInRect.x,y + dstInRect.y,pxDst);
          alpha = 255;

          if(pxSrc.length > 3) alpha = pxSrc[3];

          for(int i = 0; ( i < 3 ) && ( i < minCh ); i++)
            pxDst[i] = Math.min(255,( pxSrc[i]/255*alpha ) + ( pxDst[i] ));

          dstOut.setPixel(x,y,pxDst);
        }
      }
    }

    @Override
    public void dispose(){}
  }
}
