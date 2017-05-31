package me.satyen.code;

import java.util.LinkedList;
import java.util.List;

public class phoneNoToWord {
	public static void main(String[] args){
		System.out.println(letterCombinations("42424"));
		//the algo flows like this
		//for digit 4 it gives cobinations as
		//[g, h, i]
		//for letter 24
		//[ga, gb, gc, ha, hb, hc, ia, ib, ic]
		//for letter 424
		//[gag, gah, gai, gbg, gbh,
		//for letter 4242
		//[gaga, gagb, gagc, gaha, gahb, gahc, gaia, gaib, gaic, gbga, gbgb, gbgc, gbha, gbhb, gbhc, gbia, gbib, gbic, gcga, gcgb, gcgc, gcha, gchb, gchc, gcia, gcib, gcic, haga, hagb, hagc, haha, hahb, hahc, haia, haib, haic, hbga, hbgb, hbgc, hbha, hbhb, hbhc, hbia, hbib, hbic, hcga, hcgb, hcgc, hcha, hchb, hchc, hcia, hcib, hcic, iaga, iagb, iagc, iaha, iahb, iahc, iaia, iaib, iaic, ibga, ibgb, ibgc, ibha, ibhb, ibhc, ibia, ibib, ibic, icga, icgb, icgc, icha, ichb, ichc]gbi, gcg, gch, gci, hag, hah, hai, hbg, hbh, hbi, hcg, hch, hci, iag, iah, iai, ibg, ibh, ibi]
		//for letter 42424
		//[gagag, gagah, gagai, gagbg, gagbh, gagbi, gagcg, gagch, gagci, gahag, gahah, gahai, gahbg, gahbh, gahbi, gahcg, gahch, gahci, gaiag, gaiah, gaiai, gaibg, gaibh, gaibi, gaicg, gaich, gaici, gbgag, gbgah, gbgai, gbgbg, gbgbh, gbgbi, gbgcg, gbgch, gbgci, gbhag, gbhah, gbhai, gbhbg, gbhbh, gbhbi, gbhcg, gbhch, gbhci, gbiag, gbiah, gbiai, gbibg, gbibh, gbibi, gbicg, gbich, gbici, gcgag, gcgah, gcgai, gcgbg, gcgbh, gcgbi, gcgcg, gcgch, gcgci, gchag, gchah, gchai, gchbg, gchbh, gchbi, gchcg, gchch, gchci, gciag, gciah, gciai, gcibg, gcibh, gcibi, gcicg, gcich, gcici, hagag, hagah, hagai, hagbg, hagbh, hagbi, hagcg, hagch, hagci, hahag, hahah, hahai, hahbg, hahbh, hahbi, hahcg, hahch, hahci, haiag, haiah, haiai, haibg, haibh, haibi, haicg, haich, haici, hbgag, hbgah, hbgai, hbgbg, hbgbh, hbgbi, hbgcg, hbgch, hbgci, hbhag, hbhah, hbhai, hbhbg, hbhbh, hbhbi, hbhcg, hbhch, hbhci, hbiag, hbiah, hbiai, hbibg, hbibh, hbibi, hbicg, hbich, hbici, hcgag, hcgah, hcgai, hcgbg, hcgbh, hcgbi, hcgcg, hcgch, hcgci, hchag, hchah, hchai, hchbg, hchbh, hchbi, hchcg, hchch, hchci, hciag, hciah, hciai, hcibg, hcibh, hcibi, hcicg, hcich, hcici, iagag, iagah, iagai, iagbg, iagbh, iagbi, iagcg, iagch, iagci, iahag, iahah, iahai, iahbg, iahbh, iahbi, iahcg, iahch, iahci, iaiag, iaiah, iaiai, iaibg, iaibh, iaibi, iaicg, iaich, iaici, ibgag, ibgah, ibgai, ibgbg, ibgbh, ibgbi, ibgcg, ibgch, ibgci, ibhag, ibhah, ibhai, ibhbg, ibhbh, ibhbi, ibhcg, ibhch, ibhci, ibiag, ibiah, ibiai, ibibg, ibibh, ibibi, ibicg, ibich, ibici, icgag, icgah, icgai, icgbg, icgbh, icgbi, icgcg, icgch, icgci, ichag, ichah, ichai, ichbg, ichbh, ichbi, ichcg, ichch, ichci, iciag, iciah, iciai, icibg, icibh, icibi, icicg, icich, icici]

	}
	
    public static List<String> letterCombinations(String digits) {
    LinkedList<String> ans = new LinkedList<String>();
    String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    ans.add("");
    //for each digit from input
    for(int i =0; i<digits.length();i++){
        int x = Character.getNumericValue(digits.charAt(i));
        //for current digit find all matching chars
        while(ans.peek().length()==i){
            String t = ans.remove();
            for(char s : mapping[x].toCharArray())
                ans.add(t+s);
        }
    }
    return ans;
}
}
				