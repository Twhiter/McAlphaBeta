import info.kwarc.kalah.Agent;
import info.kwarc.kalah.KalahState;
import info.kwarc.kalah.ProtocolManager;

import java.io.IOException;

public class MonteAgentTest {


    public static void main(String[] args) throws IOException {






        MonteAgent agent = new MonteAgent("172.31.168.80", 2671, ProtocolManager.ConnectionType.TCP);


        KalahState state = new KalahState(8,8);

        int []seeds = new int[]{5,2,0,6,6,1,3,5};
        int []seeds2 = new int[]{2,2,0,0,9,1,2,10};


        for (int i = 0; i < seeds.length; i++) {
            state.setHouse(KalahState.Player.NORTH,i,seeds[i]);
            state.setHouse(KalahState.Player.SOUTH,i,seeds2[i]);
        }

        state.setStoreNorth(13);
        state.setStoreSouth(61);




        agent.search(state);

        System.out.println("");





    }




}
