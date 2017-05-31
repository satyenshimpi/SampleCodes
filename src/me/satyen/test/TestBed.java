// import org.json.simple.*;
package me.satyen.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/***
 **  @(#) TradeCard.com 1.0
 **
 **  Copyright (c) 2014 TradeCard, Inc. All Rights Reserved.
 **
 **
 **  THIS COMPUTER SOFTWARE IS THE PROPERTY OF TradeCard, Inc.
 **
 **  Permission is granted to use this software as specified by the TradeCard
 **  COMMERCIAL LICENSE AGREEMENT.  You may use this software only for
 **  commercial purposes, as specified in the details of the license.
 **  TRADECARD SHALL NOT BE LIABLE FOR ANY  DAMAGES SUFFERED BY
 **  THE LICENSEE AS A RESULT OF USING OR MODIFYING THIS SOFTWARE IN ANY WAY.
 **
 **  YOU MAY NOT DISTRIBUTE ANY SOURCE CODE OR OBJECT CODE FROM THE TradeCard.com
 **  TOOLKIT AT ANY TIME. VIOLATORS WILL BE PROSECUTED TO THE FULLEST EXTENT
 **  OF UNITED STATES LAW.
 **
 **  @version 1.0
 **  @author Copyright (c) 2014 TradeCard, Inc. All Rights Reserved.
 **
 **/

/**
 * 
 **/
public class TestBed extends Assert {

	private Collection<GTNSolution.VendingMachine.Product> AList = ImmutableList
			.of(new GTNSolution.VendingMachine.Product("A", 70));
	private Collection<GTNSolution.VendingMachine.Product> BList = ImmutableList
			.of(new GTNSolution.VendingMachine.Product("B", 50));
	private Collection<GTNSolution.VendingMachine.Product> CList = ImmutableList
			.of(new GTNSolution.VendingMachine.Product("C", 75));
	private Collection<GTNSolution.VendingMachine.Product> DList = ImmutableList
			.of(new GTNSolution.VendingMachine.Product("D", 60));
	
	private Collection<GTNSolution.VendingMachine.Coin> NoNickles = ImmutableList
			.of(GTNSolution.VendingMachine.Coin.QUARTER,
					GTNSolution.VendingMachine.Coin.DIME,
					GTNSolution.VendingMachine.Coin.DIME,
					GTNSolution.VendingMachine.Coin.DIME);

	private Collection<GTNSolution.VendingMachine.Coin> QDN2 = ImmutableList
			.of(GTNSolution.VendingMachine.Coin.QUARTER,
					GTNSolution.VendingMachine.Coin.QUARTER,
					GTNSolution.VendingMachine.Coin.DIME,
					GTNSolution.VendingMachine.Coin.DIME,
					GTNSolution.VendingMachine.Coin.NICKLE,
					GTNSolution.VendingMachine.Coin.NICKLE);

	
	// private Collection<GTNSolution.VendingMachine.Coin> Dollar = ImmutableList.of(GTNSolution.VendingMachine.Coin.DOLLAR);

	// Goal: Handle testing directly on HackerRank via STDIN / STDOUT ... 
	@Test
	public void testStockViaJson() {
		// JSONObject prodA = new JSONObject();

	}

	@Test
	public void testInitialState() {
		GTNSolution.VendingMachine machine = new GTNSolution.VendingMachineImpl();
		assertEquals(0, getTotalItemCount(machine.getItemCount()));
		assertEquals(0, getCoinAmount(machine.getCoinCount()));
	}

	@Test
	public void testBasicLoad() {
		GTNSolution.VendingMachine machine = new GTNSolution.VendingMachineImpl();
		machine.restock(AList, NoNickles);
		assertEquals(1, getTotalItemCount(machine.getItemCount()));
		assertEquals(55, getCoinAmount(machine.getCoinCount()));
	}

	@Test
	public void testNullProductArgument() {
		GTNSolution.VendingMachine machine = new GTNSolution.VendingMachineImpl();
		machine.restock(null, NoNickles);
		assertEquals(0, getTotalItemCount(machine.getItemCount()));
		assertEquals(55, getCoinAmount(machine.getCoinCount())); // 4 coins of $0.55 -- so test looks wrong.
	}

	@Test
	public void testNullCoinArgument() {
		GTNSolution.VendingMachine machine = new GTNSolution.VendingMachineImpl();
		machine.restock(AList, null);
		assertEquals(1, getTotalItemCount(machine.getItemCount()));
		assertEquals(0, getCoinAmount(machine.getCoinCount()));
	}

	@Test
	public void test30CentsChange() {
		GTNSolution.VendingMachine machine = new GTNSolution.VendingMachineImpl();
		machine.restock(AList, NoNickles);

		GTNSolution.VendingMachine.VendResult vendResult = machine.vendItem("A",
				ImmutableList.of(GTNSolution.VendingMachine.Coin.DOLLAR));
		assertEquals(30, getCoinAmount(vendResult.getChange()));
		assertEquals(0, getTotalItemCount(machine.getItemCount()));
		assertEquals(125, getCoinAmount(machine.getCoinCount()));
	}

	@Test
	public void testExactChange() {
		GTNSolution.VendingMachine machine = new GTNSolution.VendingMachineImpl();
		machine.restock(BList, NoNickles);

		GTNSolution.VendingMachine.VendResult vendResult = machine.vendItem("B",
				ImmutableList.of(GTNSolution.VendingMachine.Coin.QUARTER,
						GTNSolution.VendingMachine.Coin.QUARTER));
		
		assertEquals(0, getCoinAmount(vendResult.getChange()));
		assertEquals(0, getTotalItemCount(machine.getItemCount()));
		assertEquals(105, getCoinAmount(machine.getCoinCount()));
	}

	@Test
	public void test40CentChange() {
		GTNSolution.VendingMachine machine = new GTNSolution.VendingMachineImpl();

		machine.restock(DList, QDN2);	// D=60, QQ DD NN = 0.80

		GTNSolution.VendingMachine.VendResult vendResult = machine.vendItem("D",
				ImmutableList.of(GTNSolution.VendingMachine.Coin.DOLLAR));
		
		assertEquals(40, getCoinAmount(vendResult.getChange()));
		assertEquals(0, getTotalItemCount(machine.getItemCount()));
		assertEquals(140, getCoinAmount(machine.getCoinCount()));

	}

	@Test
	public void testEasyChange() {
		GTNSolution.VendingMachine machine = new GTNSolution.VendingMachineImpl();
		machine.restock(CList, NoNickles);

		GTNSolution.VendingMachine.VendResult vendResult = machine.vendItem("C",
				ImmutableList.of(GTNSolution.VendingMachine.Coin.DOLLAR));
		assertEquals(25, getCoinAmount(vendResult.getChange()));
		assertEquals(0, getTotalItemCount(machine.getItemCount()));
		assertEquals(130, getCoinAmount(machine.getCoinCount()));
	}

	private int getCoinAmount(Map<GTNSolution.VendingMachine.Coin, Integer> coins) {
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

	private int getCoinAmount(Collection<GTNSolution.VendingMachine.Coin> coins) {
		int totalAmount = 0;
		for (GTNSolution.VendingMachine.Coin coin : coins) {
			totalAmount += coin.getValue();
		}
		return totalAmount;
	}

	private int getTotalItemCount(Map<String, Integer> products) {
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

}
