import heap_package.Node;
import heap_package.Heap;
import java.util.ArrayList;

public class Poker{

	private int city_size;            // City Population
	public int[] money;		         // Denotes the money of each citizen. Citizen ids are 0,1,...,city_size-1. 
	private Heap loss;
	private Heap profit;
	private Heap mn;
	private Heap mx;
	/* 
	   1. Can use helper methods but they have to be kept private. 
	   2. Allowed to use only PriorityQueue data structure globally but can use ArrayList inside methods. 
	   3. Can create at max 4 priority queues.
	   */
	  
	  public Poker(int city_size, int[] players, int[] max_loss, int[] max_profit){
		  System.out.println("error poker");
  
		  /* 
			 1. city_size is population of the city.
			 1. players denotes id of the citizens who have come to the Poker arena to play Poker.
			 2. max_loss[i] denotes the maximum loss player "players[i]"" can bear.
			 3. max_profit[i] denotes the maximum profit player "players[i]"" will want to get.
			 4. Initialize the heap data structure(if required). 
			 n = players.length 
			 Expected Time Complexity : O(n).
		  */
  
		  this.city_size = city_size;
		  this.money = new int[this.city_size];
		  this.initMoney();
		  // To be filled in by the student
		  try{
			  for (int i=0; i<players.length; i++){
				  max_profit[i]*=-1;
				  max_loss[i]*=-1;
			  }
			  profit = new Heap(city_size, players, max_profit);
			  loss = new Heap(city_size, players, max_loss);
			  int[] val = new int[players.length];
			  int[] val1 = new int[players.length];
			  for (int i = 0; i<players.length; i++){
				  val1[i] = -money[players[i]];
				  val[i] = money[players[i]];
			  }
			  mx = new Heap(city_size, players, val);
			  mn = new Heap(city_size, players, val1);
		  } catch (Exception e){
			  System.out.println(e);
		  }
  
	  }
	public void initMoney(){
		// Do not change this function.
		for(int i = 0;i<city_size;i++){
			money[i] = 100000;							// Initially all citizens have $100000. 
		}
	}


	public ArrayList<Integer> Play(int[] players, int[] bids, int winnerIdx){

		System.out.println("error play");
		/* 
		   1. players.length == bids.length
		   2. bids[i] denotes the bid made by player "players[i]" in this game.
		   3. Update the money of the players who has played in this game in array "money".
		   4. Returns players who will leave the poker arena after this game. (In case no
		      player leaves, return an empty ArrayList).
                   5. winnerIdx is index of player who has won the game. So, player "players[winnnerIdx]" has won the game.
		   m = players.length
		   Expected Time Complexity : O(mlog(n))
		*/
		int maxo = 1000*1000*1000;
		int winner = players[winnerIdx];					// Winner of the game.

		ArrayList<Integer> playersToBeRemoved = new ArrayList<Integer>();     // Players who will be removed after this game. 

		// To be filled in by the student
		int cur = 0;
		for (int x:bids){
			cur+=x;
		}
		for (int i = 0; i<players.length; i++){
			try{
				loss.update(players[i], bids[i]);
				money[players[i]]-=bids[i];
				mn.update(players[i], bids[i]);
				mx.update(players[i], -bids[i]);
				if (i==winnerIdx){
					profit.update(players[i], -bids[i]);
				}
			} catch (Exception e){
				System.out.println(e);
			}
		}
		try{
			profit.update(players[winnerIdx], cur);
			loss.update(players[winnerIdx], -cur);
			money[players[winnerIdx]]+=cur;
			mn.update(players[winnerIdx], -bids[winnerIdx]);
			mx.update(players[winnerIdx], bids[winnerIdx]);
	} catch (Exception e){
			System.out.println(e);
		}
		ArrayList<Integer> arr = new ArrayList<>();
		try{
			while (loss.getMaxValue()>0){
				arr = loss.getMax();
				for(Integer x:arr){
					playersToBeRemoved.add(x);
					loss.deleteMax();
				}
			}
			for (int x:playersToBeRemoved){
				try{
					profit.update(x, maxo);
					profit.deleteMax();
				} catch (Exception e){
					;
				}
				try{
					mn.update(x, maxo);
					mx.deleteMax();
				} catch (Exception e){
					;
				}
				try{
					mn.update(x, maxo);
					mn.deleteMax();
				} catch (Exception e){
					;
				}
			}
		} catch (Exception e){
			System.out.println(e);
		}
		try{
			while (profit.getMaxValue()>0){
				arr = profit.getMax();
				for(Integer x:arr){
					try{
						playersToBeRemoved.add(x);
						profit.deleteMax();
					} catch (Exception e){}
				}
			}
			for (int x:playersToBeRemoved){
				try{
					loss.update(x, maxo);
					loss.deleteMax();
				} catch (Exception e){
					;
				}
				try{
					mx.update(x, maxo);
					mx.deleteMax();
				} catch (Exception e){
					;
				}
				try{
					mn.update(x, maxo);
					mn.deleteMax();
				} catch (Exception e){
					;
				}
			}
		} catch (Exception e){
			System.out.println(e);
		}
		return playersToBeRemoved;
	}

	public void Enter(int player, int max_loss, int max_profit){
		System.out.println("error enter");

		/*
			1. Player with id "player" enter the poker arena.
			2. max_loss is maximum loss the player can bear.
			3. max_profit is maximum profit player want to get. 
			Expected Time Complexity : O(logn)
		*/
		try{
			loss.insert(player, -max_loss);
			profit.insert(player, -max_profit);
			mn.insert(player, -money[player]);
			mx.insert(player, money[player]);
		} catch (Exception e){
			System.out.println(e);
		}
		// To be filled in by the student
	}

	public ArrayList<Integer> nextPlayersToGetOut(){

		/* 
		   Returns the id of citizens who are likely to get out of poker arena in the next game. 
		   Expected Time Complexity : O(1). 
		*/
		System.out.println("error");
		ArrayList<Integer> players = new ArrayList<Integer>();    // Players who are likely to get out in next game.

		// To be filled in by the student

		return players;
	}

	public ArrayList<Integer> playersInArena(){
		System.out.println("error in arena");

		/* 
		   Returns id of citizens who are currently in the poker arena. 
		   Expected Time Complexity : O(n).
		*/
		ArrayList<Integer> currentPlayers = new ArrayList<>();    // citizens in the arena.

		try{
			currentPlayers = loss.getKeys();    // citizens in the arena.
		} catch (Exception e){
			System.out.println(e);
		}
		
		// To be filled in by the student
		return currentPlayers;

	}

	public ArrayList<Integer> maximumProfitablePlayers(){
		System.out.println("error profutmax");

		/* 
		   Returns id of citizens who has got most profit. 
			
		   Expected Time Complexity : O(1).
		*/

		ArrayList<Integer> citizens = new ArrayList<>();    // citizens with maximum profit.
		try{
			citizens = mx.getMax();
		} catch (Exception e){
			System.out.println(e);
		}
		
		return citizens;
		// To be filled in by the student

	}

	public ArrayList<Integer> maximumLossPlayers(){
		System.out.println("error lossmax");
		/* 
		   Returns id of citizens who has suffered maximum loss. 
			
		   Expected Time Complexity : O(1).
		*/
		ArrayList<Integer> citizens = new ArrayList<>();     // citizens with maximum loss.

		try{
			citizens = mn.getMax();
		} catch (Exception e){
			System.out.println(e);
		}
		
		// To be filled in by the student
		return citizens;

	}

}