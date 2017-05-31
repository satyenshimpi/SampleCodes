package me.satyen.test;

//Out of memory error test
public class OOMTest {
	public static void main(String[] args) {
		String str = "kjhsdkfhoueowjdiqjdiqwdasnnsdkjhsdkfhoueowjdiqjdiqwdasnnsdkjhsdkfhoueowjdiqjdiqwdasnnsdkjhsdkfhoueowjdiqjdiqwdasnnsdkjhsdkfhoueowjdiqjdiqwdasnnsdkjhsdkfhoueowjdiqjdiqwdasnnsdkjhsdkfhoueowjdiqjdiqwdasnnsdkjhsdkfhoueowjdiqjdiqwdasnnsdkjhsdkfhoueowjdiqjdiqwdasnnsdkjhsdkfhoueowjdiqjdiqwdasnnsdkjhsdkfhoueowjdiqjdiqwdasnnsd";
		int cnt = 0;
		while(true){
			for(int i=0; i<cnt; i++){
				str += new String(str);
			}
			System.out.println(cnt);
			cnt++;
			if(cnt==4){
				System.out.println();
			}
		}
		
		//for stack over flow error
//		main(args);
	}
}
