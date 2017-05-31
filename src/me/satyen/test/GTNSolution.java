package me.satyen.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class GTNSolution {

	public static void main(String args[]) throws Exception {
		// do not alter this this method.
		// code your solution in the VendingMachineImpl $TODO method stubs
		// located below.
		Collection<VendingMachine.Product> products = null;
		Collection<VendingMachine.Coin> coins = null;
		String itemName = null;
		Collection<VendingMachine.Coin> tender = null;
		GTNSolution.VendingMachine.VendResult result = null;

		Scanner input = new Scanner(System.in);
		String line = input.nextLine();
		if (line != null && !"".equals(line.trim())) {
			/*
			 * Product line is like A 50 B 75
			 */
			Scanner productLine = new Scanner(line);
			products = new ArrayList<VendingMachine.Product>();
			while (productLine.hasNext()) {
				String name = productLine.next();
				Integer price = productLine.nextInt();
				products.add(new VendingMachine.Product(name, price));
			}
		}

		line = input.nextLine();
		if (line != null && !"".equals(line.trim())) {
			/*
			 * Coin line contains names of coins in stock, such as QUARTER DIME
			 * DIME DIME
			 */
			Scanner coinLine = new Scanner(line);
			coins = new ArrayList<VendingMachine.Coin>();
			while (coinLine.hasNext()) {
				String coinName = coinLine.next();
				VendingMachine.Coin c = VendingMachine.Coin.valueOf(coinName);
				if (c != null) {
					coins.add(c);
				}
			}
		}

		line = input.nextLine();
		if (line != null && !"".equals(line.trim())) {
			/*
			 * Vend line starts with product name followed by coin names A
			 * DOLLAR
			 */
			Scanner vendLine = new Scanner(line);
			tender = new ArrayList<VendingMachine.Coin>();
			if (vendLine.hasNext()) {
				itemName = vendLine.next();
				while (vendLine.hasNext()) {
					VendingMachine.Coin c = VendingMachine.Coin
							.valueOf(vendLine.next());
					if (c != null) {
						tender.add(c);
					}
				}
			}
		}

		VendingMachine vm = new VendingMachineImpl();
		vm.restock(products, coins);

		if (itemName != null) {
			result = vm.vendItem(itemName, tender);
		}

		System.out.print(getTotalItemCount(vm.getItemCount()));
		System.out.print(" ");
		System.out.print(getCoinAmount(vm.getCoinCount()));

		if (result != null) {
			System.out.print(" ");
			System.out.print(getCoinAmount(result.getChange()));
		}
	}

	// do not alter this this method.
	private static int getTotalItemCount(Map<String, Integer> products) {
		int totalCount = 0;
		if (products != null) {
			for (Integer count : products.values()) {
				if (count != null && count.intValue() > 0) {
					totalCount += count.intValue();
				}
			}
		}
		return totalCount;
	}

	// do not alter this this method.
	private static int getCoinAmount(
			Collection<GTNSolution.VendingMachine.Coin> coins) {
		int totalAmount = 0;
		for (GTNSolution.VendingMachine.Coin coin : coins) {
			totalAmount += coin.getValue();
		}
		return totalAmount;
	}

	// do not alter this this method.
	private static int getCoinAmount(
			Map<GTNSolution.VendingMachine.Coin, Integer> coins) {
		int totalAmount = 0;
		if (coins != null) {
			for (GTNSolution.VendingMachine.Coin coin : coins.keySet()) {
				Integer count = coins.get(coin);
				if (count != null && count.intValue() > 0) {
					totalAmount += (count.intValue() * coin.getValue());
				}
			}
		}
		return totalAmount;
	}

  
  
	/**
	 * Vending Machine
	 * 
	 * A coin-operated machine to automate the sale and dispensing of items to a
	 * customer.
	 */
	public interface VendingMachine {

		/**
		 * The restock method is intended to be used by the machine owner to
		 * stock item inventory and/or coin inventory into the vending machine.
		 *
		 * This method may be invoked more than once in the life-cycle of an
		 * instance.
		 * 
		 * @param items
		 *            - the products to add the product inventory
		 * @param funds
		 *            - the coins to add to the coin inventory
		 */
		public void restock(Collection<Product> items, Collection<Coin> coins);

		/**
		 * The vend method is the principle purchase method. It returns the
		 * amount of change in cents when able to vend. When unable to vend for
		 * any reason this method returns the full amount tendered.
		 * 
		 * Money tendered less any change becomes funds if and only if able to
		 * vend.
		 *
		 * This method may be invoked more than once in the life-cycle of an
		 * instance.
		 * 
		 * @param itemName
		 *            - the name of the product desired to purchase
		 * @param tender
		 *            - the coins offered for payment
		 * @return change (if due) in coins (or an empty collection) when no
		 *         change is due and the full tender amount when unable to vend.
		 */
		public VendResult vendItem(String itemName, Collection<Coin> tender);

		/**
		 * Inquires for the product inventory
		 * 
		 * @return the current state of product inventory or null when no
		 *         products exist.
		 */
		public Map<String, Integer> getItemCount();

		/**
		 * Inquires for the funds inventory
		 * 
		 * @return the current state of the funds inventory or null when no
		 *         funds exist.
		 */
		public Map<Coin, Integer> getCoinCount();

		/**
		 * Coin Enum - US based currency Each token represents a kind of coin
		 * and its value, in cents (USD). Note: Penny(1) //$0.01 is
		 * intentionally not defined
		 */
		public enum Coin {
			DOLLAR(100), // $1.00
			QUARTER(25), // $0.25
			DIME(10), // $0.10
			NICKLE(5); // $0.05

			int cents = 0;

			Coin(int cents) {
				this.cents = cents;
			}

			public int getValue() {
				return cents;
			}
		};

		/**
		 * Product - An Item. Items have a name and price. Prices for an item
		 * are instance controlled by its name. All items with the same name
		 * have the same price.
		 */
		public static final class Product {

			private static final Map<String, Integer> priceMap = new HashMap();

			private final String itemName;

			Product(String itemName, int price) {
				this.itemName = String.valueOf(itemName);
				priceMap.put(itemName, Integer.valueOf(price));
			}

			public String getItemName() {
				return itemName;
			}

			public int getPrice() {
				return priceMap.get(itemName).intValue();
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result
						+ ((itemName == null) ? 0 : itemName.hashCode());
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Product other = (Product) obj;
				if (itemName == null) {
					if (other.itemName != null) {
						return false;
					}
				} else if (!itemName.equals(other.itemName)) {
					return false;
				}
				return true;
			}

			@Override
			public String toString() {
				return "Product [itemName=" + itemName + ", price="
						+ getPrice() + "]";
			}

		}

		/**
		 * VendResult - Captures the results of a vend attempt.
		 * 
		 * @item - the item purchased
		 * @change - the coins returned
		 */
		public static final class VendResult {
			private final Product item;
			private final Collection<Coin> change;

			public VendResult(Product product, Collection<Coin> change) {
				this.item = product;
				this.change = change;
			}

			public Product getItem() {
				return item;
			}

			public Collection<Coin> getChange() {
				return change;
			}
		}
	}

	/*************************************************************
	 * Add your implementation code to the VendingMachineImpl stub
	 *************************************************************/
	public static class VendingMachineImpl implements VendingMachine {

            /** Inventory of Product Items */
            ArrayList<Product> itemInventory = new ArrayList<Product>();
            /** Inventory of Funds available in Machine */
            ArrayList<Coin> fundsInventory = new ArrayList<Coin>();

		@Override
		public void restock(Collection<Product> items, Collection<Coin> coins) {
			// $TODO implement restock
                    //Add the input items to product inventory
                    if(items != null)
                        addToProducts(items);
                    //Add the input Amount to coins inventory
                    if(coins != null)
                        addToFunds(coins);
		}

		@Override
		public VendResult vendItem(String itemName, Collection<Coin> tender) {
                    VendResult ret = null;
                    //First need to confirm if Product to be returned is available in inventory                    
                    /** Create the Product object reference for Product to be returned as vend result */
                    Product prod = null;
                    for (int i = 0; i < itemInventory.size(); i++) {    //iterate over all available items
                        Product product = itemInventory.get(i);
                        if(product.getItemName().equals(itemName)){     //Match Product by its name
                            prod = product;                             //Get the product to be returned as vend result
                            break;                                      //break the loop when found
                        }
                    }
                    
                    /** Create the Coins object reference for coins to be returned as vend result */
                    Collection<Coin> retCoins = null;                   //calculate the change to be returned or return the full amount
                    if(itemInventory.contains(prod)){                   //if available then dispense
                        int tmpPrice = itemInventory.get(itemInventory.indexOf(prod)).getPrice();
                        retCoins = getChangeToRet(tender, tmpPrice);
                        if(retCoins != null){                           //if machine is able to return the change and product                            
                            itemInventory.remove(prod);                 //remove the product from itemInventory
                            ret = new VendResult(prod, retCoins);       //create a Vend Result
                        }
                    }
                    return ret;
		}

		@Override
		public Map<String, Integer> getItemCount() {
			// $TODO implement getItemCount
                    Map<String, Integer> ret = new HashMap();
                    for (int i = 0; i < itemInventory.size(); i++) {
                        Product product = itemInventory.get(i);
                        if(ret.containsKey(product.getItemName()))
                            ret.put(product.getItemName(), ret.get(product.getItemName())+1);
                        else
                            ret.put(product.getItemName(), 1);
                    }
                    return ret;
		}

		@Override
		public Map<Coin, Integer> getCoinCount() {
			// $TODO implement getCoinCount
                    Map<Coin, Integer> ret = new HashMap();
                    for (int i = 0; i < fundsInventory.size(); i++) {
                        Coin coin = fundsInventory.get(i);
                        if(ret.containsKey(coin))
                            ret.put(coin, ret.get(coin)+1);
                        else
                            ret.put(coin, 1);
                    }
                    return ret;
		}

        /**
         * Add products to Product inventory
         * @param items items to be added
         */
        private void addToProducts(Collection<Product> items) {
            for (Iterator<Product> it = items.iterator(); it.hasNext();) {
                Product product = it.next();
                itemInventory.add(product);
            }
        }
        /**
         * Adds to coins inventory
         * @param coins coins to be added
         */
        private void addToFunds(Collection<Coin> coins) {
            for (Iterator<Coin> it = coins.iterator(); it.hasNext();) {
                Coin coin = it.next();
                fundsInventory.add(coin);
            }
        }

        /**
         * Calculates the amount to be returned
         * @param tender input amount
         * @param price price of the Product
         * @return the coin change as HashMap
         */
        private Collection<Coin> getChangeToRet(Collection<Coin> tender, int price) {
            /** HashMap for holding amount denominations and ArrayList of Coins required for that denomination
             For 20 cents denomination the Map entry will be as <20, List(DIME, DIME)> that is 20 cents made up of two DIMES
             * The Map will hold all denominations from 0 to the amount to be returned (price - total)
             */
            HashMap<Integer, ArrayList<Coin>> coinrHM = new HashMap<Integer, ArrayList<Coin>>();
            
            /** the Coin collection to be returned */
            Collection<Coin> ret = null;
            
            //First calculate the total inserted amount by adding all coins
            int total = 0;
            for (Iterator<Coin> it = tender.iterator(); it.hasNext();) {
                total += it.next().getValue();
            }
            
            //check if total is less than price
            if(total < price){
                return ret;                         //then return the full tender amount
            }
            else if(total==price){                  //check if total is exactly same as price
                //then add the coins to inventory
                addToFunds(tender);
                //and return 0 (Zero) coins
                return new ArrayList<Coin>(0);
            }
            else{                                   //else calculate the change to be returned
                //Using Dynamic Programming(DP) approach to find the solution
                /**  */
                int[] CoinReq = new int[total+1];
                
                //Base case for change '0' is 0
                CoinReq[0]=0;
                //Base case for change '0' is ArrayList of 0 size
                coinrHM.put(0, new ArrayList<Coin>());
                
                for (int i = 1; i < CoinReq.length; i++) {
                    CoinReq[i]=Integer.MAX_VALUE-1;
                    //All other values are initially made as empty ArrayList
                    coinrHM.put(i, new ArrayList<Coin>());
                }
                
                //Trying Dynamic Programming (DP) to find solution
                //For all denominations from 1 to the amount to be returned (total - price), calculate the best combination of change
                //example: for 20 cents change two DIMEs are good than four NICKELS
                for(int i=1; i<= total - price; i++){
                    //copy original inventory to temporary inventory so that when we remove coins from inventory 
                    //and later found good solution then the removed item can not be replaced
                    ArrayList<Coin> tmpInv= new ArrayList();
                    for (int j = 0; j < fundsInventory.size(); j++) {
                        tmpInv.add(fundsInventory.get(j));                        
                    }
//                    for(int j=0; j<tmpInv.size(); j++){
                    Iterator<Coin> it = tmpInv.iterator();
                    //Now iterate over each coin in temporary inventory
                    while(it.hasNext()){
                        Coin coin = it.next();
                        int currentCoinValue = coin.getValue();
                        if( currentCoinValue <= i ){                         //check if coin value is less than or equal to i then we can use that coin to make i amount
                            //If coin value is less than i then we need to get get the solution for remaining i-currentCoinValue amount
                            //for that first we need to check if the solution for i-currentCoinValue already exists
                            if(coinrHM.containsKey(i-currentCoinValue)){
                                //if i-currentCoinValue is zero or i-currentCoinValue consist of 1 or more coins 
                                //then we need got solution to make up i using current coin
                                if(i-currentCoinValue==0 || coinrHM.get(i-currentCoinValue).size()>0){
                                    ArrayList<Coin> coinArrayToMakeUpChange = new ArrayList<Coin>();
                                    it.remove();                                                        //remove the current coin from temp inventory
                                    coinArrayToMakeUpChange.add(coin);                                  //add current coin to ArrayList to make up i
                                    coinArrayToMakeUpChange.addAll(coinrHM.get(i-currentCoinValue));    //add all elements of ArrayList to make up (i-currentCoinValue) to i's ArrayList
                                    
                                    //replace previous results if we found i by using minimum number of coins than previous solution
                                    if(coinrHM.get(i).size()==0 || coinrHM.get(i).size()>coinArrayToMakeUpChange.size())
                                        coinrHM.put(i, coinArrayToMakeUpChange);
                                }
                            }
                        }
                    }
                }
                //assign the solution found (ArrayList of coins) for (total - price) to return reference
                ret = coinrHM.get(total - price);
            }
            //add input tender to funds
            addToFunds(tender);
            //remove coins to be returned from inventory
            for (Iterator<Coin> it = ret.iterator(); it.hasNext();) {
                Coin coin = it.next();                
                fundsInventory.remove(coin);
            }            
            return ret;
        }

	}


}
