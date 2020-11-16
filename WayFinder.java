package project5;

import java.util.Arrays;

/**
 * This class is a program that finds all solutions to a number puzzle.
 * It uses array of positive integers to find a path from index zero to the last index in the array. 
 * Solutions have the distance to be traveled and the directions(Right or Left)
 * It uses recursive algorithm to find all ways through the puzzle. 
 * @author Heewon Kim
 *
 */
public class WayFinder {

	public static void main(String[] args) {
		
		int nums[]=new int[args.length];
		//case where no argument is given 
		if (args.length==0){
			System.out.println("Error: incorrect usage. At least one argument is required");
			System.exit(0);
		}
		
		//putting the argument into number array
		for (int i=0;i<nums.length;i++){
        	nums[i]=Integer.parseInt(args[i]);
        }
		
        //checks to see if the last number is zero 
        if (nums[nums.length-1]!=0){
        	System.out.println("Error: the last puzzle value needs to be zero");
        	System.exit(0);
        }
        
        // checks if the values are all positive integers except the last number
        for (int i=0;i<nums.length;i++){
        	if (nums[i]<0 ||nums[i]>100){
        		System.out.println("Error: the puzzle values have to be positive integers in range [0,99].");
        		System.exit(0);
        
        	}
        }
        //if only 0 in the entry  
        if (nums.length==1){
        	System.out.println("[ 0 ]");
        	System.out.println("There is 1 way through the puzzle.");
        }
        
        else{
        String [] result = new String[nums.length];
        result[0]=nums[0]+"R";
        	for (int i=1;i<nums.length;i++){
        		result[i]=nums[i]+"";
        	}
       
        int totalsol = findways(nums[0],nums,result);
        if (totalsol==0)
        	System.out.println("No way through this puzzle.");
        else if (totalsol==1){
        	System.out.println("There is 1 way through this puzzle.");
        }
        else{
        	System.out.println("There are "+totalsol+" ways through the puzzle.");
        }
        }
    	
        
	}
		
	/**
	 * Helper method that prints out the array in a certain format. 
	 * @param array String array that will be formatted 
	 */
	
	public static void printways (String[] array){
		String str="";
		if (Integer.parseInt(array[0].substring(0,array[0].length()-1))<10){
			str="[ "+array[0];
	}
		if (array.length==1){
			str+=" ]";
		}
		if (Integer.parseInt(array[0].substring(0,array[0].length()-1))>=10){
			str+="["+array[0];
		}
		if(array.length!=1){
			for (int i=1;i<array.length;i++){
				if(array[i].contains("R")||array[i].contains("L"))
					str+=", "+String.format("%3s", array[i]);
				else
					str+=", "+String.format("%2s",array[i])+" ";
			}
			str+="]";
		}
		System.out.println(str);
	}
	
	
	/**
	 * Generate paths from index zero to the last index in the array and calculate 
	 * the total number of paths through recursion 
	 * When it reaches the base case, it calls to the printways method to print arrays in the right format. 
	 * @param index index that are used to go through arrays 
	 * @param nums array of valid integers from the command line
	 * @param output array of that gets modified if it moves right or left 
	 * @return number of solutions to a number puzzle  
	 */
	
	public static int findways(int index,int []nums , String[]output) {
    	//when the new index right out of bounds
    	if (index>nums.length-1){
    		return 0;
    	}
    	//when the new index right out of bounds
    	if (index<0){
    		return 0;
    	}
    	//base case
    	if (index==nums.length-1){
    		printways(output);
    		return 1;
    	}
    	//to block infinite loop
    	if (output[index].contains("R")||output[index].contains("L")){
    		return 0;
    	}
    	
    	
    	//Recursive case - move right R 
    	output[index]=output[index]+"R";
    	int solution1= findways(index+nums[index],nums, output);
    	output[index]=output[index].substring(0,output[index].length()-1);
    	
    	//Recursive case - move left L 
    	output[index]=output[index]+"L";
    	int solution2= findways(index-nums[index],nums, output);
    	output[index]=output[index].substring(0,output[index].length()-1);
    	
    	return solution1+solution2;
    }
	
	
	
	
	
	
	
	
	
	
	
	
}
