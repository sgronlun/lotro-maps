package delta.games.lotro.maps.data.markers;

/**
 * Simple tool class to get the landblock reference from a marker identifier.
 * @author DAM
 */
public class MainGetLandBlockFromMarkerId
{
  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    int markerId=523939870;
    int region=(markerId&0x70000000)>>28;
    int bigXBlock=(markerId&0xF000000)>>24;
    int bigYBlock=(markerId&0xF00000)>>20;
    int smallBlockX=(markerId&0xF0000)>>16;
    int smallBlockY=(markerId&0x0F000)>>12;
    int blockX=(bigXBlock<<4)+smallBlockX;
    int blockY=(bigYBlock<<4)+smallBlockY;
    System.out.println("R="+region+", BX="+blockX+", BY="+blockY);
  }
}
