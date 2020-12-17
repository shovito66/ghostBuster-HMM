package basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
    int totalRowNo;
    int totalColNo;
    int totalBlocks;
    int size;
    Ghost ghost;
    ArrayList<Block> blockList=new ArrayList<>();
    public ArrayList<Double> updatedTransitionalProb=new ArrayList<>();
    private double[][] graphArray;
    public int max_manhattan_distance;
    public ArrayList<Double> updated_e_prob=new ArrayList<>();

    public Board(int totalRowNo, int totalColNo) {
        this.totalRowNo = totalRowNo;
        this.totalColNo = totalColNo;
        this.totalBlocks = totalRowNo*totalColNo;
        this.blockList = new ArrayList<>();
        graphArray = new double[totalRowNo][totalColNo];
        this.max_manhattan_distance = totalRowNo + totalColNo - 2;
        for (double[] row: graphArray)
            Arrays.fill(row, 0);
    }

    public void makeBoard(){
        for( int i = 0 ; i < this.totalRowNo ; i++ ){
            //ei loop e  j er jagay no of clm dilei m*n howar kotha.
            for( int j = 0 ; j < this.totalColNo ; j++ ){
                double prob=100.0/totalBlocks;
                Block b=new Block(i,j,totalRowNo,totalColNo,prob);
                graphArray[i][j] = prob;
                this.blockList.add(b);
            }
        }
    }

    public Ghost createGhost(){
        Random rand = new Random();
        int r=rand.nextInt(totalRowNo);
        int c=rand.nextInt(totalRowNo);
        System.out.println("Ghost is in Row:"+r+" -- Col:"+c);
        Block b = getBlock(r,c);
        this.ghost = new Ghost(r,c,b);
        return this.ghost;
    }

   ///--------------------------MOVE GHOST-----------------------///
   public void moveghost(){
       int updated_row_no=ghost.current_block.getRowNo();
       int updated_col_no=ghost.current_block.getColNo();
       int i=getRandomnum();

       if (ghost.current_block.getBlockType()==BlockType.MIDDLE){
           if (i<21){//right
               updated_col_no++;
           }
           else if(i<41){//left
               updated_col_no--;
           }
           else if(i<61){//up
               updated_row_no--;
           }
           else if(i<81){//down
               updated_row_no++;
           }
           else if(i<85){//up_right
               updated_row_no--;
               updated_col_no++;
           }
           else if(i<89){//down_right
               updated_row_no++;
               updated_col_no++;
           }
           else if(i<93){//up_left
               updated_row_no--;
               updated_col_no--;
           }
           else if(i<97){//down_left
               updated_row_no++;
               updated_col_no--;
           }
           else{//self, no change

           }


       }
       else if (ghost.current_block.getBlockType()==BlockType.CORNER){
           if(i<71){//uldr move. these will have any 2 of these 4 moves.
               if (i<36){//left or right move.
                   if (check_column_validity(updated_col_no+1))updated_col_no++;//right
                   else if (check_column_validity(updated_col_no-1))updated_col_no--;//left
               }
               else{////up or down move
                   if (check_row_validity(updated_row_no-1))updated_row_no--;//up
                   else if (check_row_validity(updated_row_no+1))updated_row_no++;//down
               }
           }
           else if(i<91){//diagonal
               if(check_row_validity(updated_row_no-1)&&check_column_validity(updated_col_no+1)){
                   updated_row_no--;
                   updated_col_no++;
               }//up_right
               else if(check_row_validity(updated_row_no+1)&&check_column_validity(updated_col_no+1)){
                   updated_row_no++;
                   updated_col_no++;
               }////down_right
               else if(check_row_validity(updated_row_no-1)&&check_column_validity(updated_col_no-1)){//up_left
                   updated_row_no--;
                   updated_col_no--;
               }
               else if(check_row_validity(updated_row_no+1)&&check_column_validity(updated_col_no-1)){
                   updated_row_no++;
                   updated_col_no--;
               }//down_left

           }
           else{//self

           }


       }
       else if (ghost.current_block.getBlockType()==BlockType.SIDE){
           if(check_row_validity(updated_row_no-1) && check_row_validity(updated_row_no+1)){//right/left side er block
               //up,down both valid. r or l valid.  u_r,d_r or u_l,d_l both valid,
               if (i<51){
                   if (i<26){
                       //up
                       updated_row_no--;
                   }
                   else {
                       //down
                       updated_row_no++;
                   }
               }
               else if (i<76){
                   //left or right
                   if (check_column_validity(updated_col_no+1))updated_col_no++;//right
                   else if (check_column_validity(updated_col_no-1))updated_col_no--;//left
               }
               else if(i<96){
                   if (i<86){
                       //ur or ul
                       if(check_row_validity(updated_row_no-1)&&check_column_validity(updated_col_no+1)){
                           //ur
                           updated_row_no--;
                           updated_col_no++;

                       }
                       else if(check_row_validity(updated_row_no-1)&&check_column_validity(updated_col_no-1)){//up_left
                           updated_row_no--;
                           updated_col_no--;
                       }

                   }
                   else {
                       //dr or dl
                       if(check_row_validity(updated_row_no+1)&&check_column_validity(updated_col_no+1)){
                           //dr
                           updated_row_no++;
                           updated_col_no++;
                       }
                       else if(check_row_validity(updated_row_no+1)&&check_column_validity(updated_col_no-1)){
                           updated_row_no++;
                           updated_col_no--;
                       }//down_left
                   }
               }



           }
           else{//up/down side er block
               //l,r both valid. u or d valid. d_r,d_l or u_r,u_l both valid.
               if(i<51){
                   if (i<26){
                       //left
                       if (check_column_validity(updated_col_no-1))updated_col_no--;
                   }
                   else {
                       //right
                       if (check_column_validity(updated_col_no+1))updated_col_no++;
                   }
               }
               else if (i<76){
                   //up or down
                   if (check_row_validity(updated_row_no-1)){
                       //up
                       updated_row_no--;
                   }
                   else{
                       //down
                       if (check_row_validity(updated_row_no+1))updated_row_no++;
                   }

               }
               else if (i<96){
                   if (i<86){
                       //dr or ur
                       if(check_row_validity(updated_row_no+1)&&check_column_validity(updated_col_no+1)){
                           //down_right
                           updated_row_no++;
                           updated_col_no++;
                       }
                       else if(check_row_validity(updated_row_no-1)&&check_column_validity(updated_col_no+1)){
                           updated_row_no--;
                           updated_col_no++;
                       }//up_right
                   }
                   else{
                       //dl or ul
                       if(check_row_validity(updated_row_no+1)&&check_column_validity(updated_col_no-1)){
                           updated_row_no++;
                           updated_col_no--;
                       }//down_left
                       else if(check_row_validity(updated_row_no-1)&&check_column_validity(updated_col_no-1)){//up_left
                           updated_row_no--;
                           updated_col_no--;
                       }

                   }
               }


           }

       }


//        ghost.current_block.position.setRow(udpated_row_no);
//        ghost.current_block.position.setColumn(updated_col_no);
       //failsafe for invalid position.
       if (check_row_validity(updated_row_no) && check_column_validity(updated_col_no)){
           if (updated_row_no!=ghost.current_block.getRowNo() || updated_col_no!=ghost.current_block.getColNo()){
               ghost.current_block=getBlock(updated_row_no,updated_col_no);
           }
       }
       System.out.println("ghost position : "+ghost.current_block.getRowNo()+"--"+ghost.current_block.getColNo());

   }

    public int getRandomnum(){
        Random rand = new Random();
        int r=rand.nextInt(100)+1;
        return r;
    }

    public boolean check_row_validity(int a){
        if (a<0 || a>this.totalRowNo-1)return false;
        return true;
    }

    public boolean check_column_validity(int a){
        if (a<0 || a>this.totalColNo-1)return false;
        return true;
    }
    ///------------------END-----------------------------------------

    public void printBoard(){
        for (Block x: blockList) {
            graphArray[x.getRowNo()][x.getColNo()] = x.getCurrentProbability();
        }

        //System.out.println("Length:"+nodeList.size());
        for (int i = 0; i <totalRowNo ; i++) {
            for (int j = 0; j <totalColNo ; j++) {
                System.out.printf("%.3f\t",graphArray[i][j]);
            }
            System.out.println();
        }

//        for (int i = 0; i <this.totalBlocks; i++) {
//            System.out.printf("%.3f\t",getBlockList().get(i).getCurrentProbability());
//            if (i%getRowNo()==0) System.out.println();
//        }


    }

    public Block getBlock(int row,int col){
        for (Block blk: this.blockList) {
            if (blk.getRowNo()==row & blk.getColNo()==col)
                return blk;
        }
        return null;
    }

    public void re_initialize_block_colors(){
        for (Block b:this.blockList) {
            b.colour = CONSTANTS.WHITE;
        }
    }

    //------------------------------codes for sensing part-----------------------------------
    public int measure_color_to_show(int row1,int col1,int row2,int col2){
        int dist=0;
        dist=Math.abs(row1-row2)+Math.abs(col1-col2);
        Double d1=Double.valueOf(dist);
        Double d2= Double.valueOf(max_manhattan_distance);
        Double ratio=d1/d2;
        if (ratio<CONSTANTS.RATIO_FOR_RED)return CONSTANTS.RED;
        else if (ratio<CONSTANTS.RATIO_FOR_ORANGE)return CONSTANTS.ORANGE;
        else return CONSTANTS.GREEN;
    }

    public void assign_color_to_sensed_block(int sensed_row,int sensed_col){
        Block sensed_block=getBlock(sensed_row,sensed_col);
        sensed_block.colour=measure_color_to_show(sensed_row,sensed_col,ghost.current_block.getRowNo(),ghost.current_block.getColNo());
        String Color;
        if (sensed_block.colour==CONSTANTS.RED) Color="RED";
        else if(sensed_block.colour==CONSTANTS.ORANGE) Color="ORANGE";
        else Color="GREEN";
        System.out.println("Sensor Colour for Block row:"+sensed_row+" --col"
                +sensed_col+"\t--> COLOR:"+Color);
    }

    public void update_probability_for_emission(int sensed_row,int sensed_col){
        Block sensed_block = getBlock(sensed_row,sensed_col);
        double normalization_sum=0;
        for (int i = 0; i <this.totalBlocks ; i++) {
            double new_prob=0;

            Block cur_block = getBlockList().get(i);
            int right_color_at_sensed_block = measure_color_to_show(cur_block.getRowNo(),cur_block.getColNo(),sensed_row,sensed_col);
            if (right_color_at_sensed_block==sensed_block.colour){
                new_prob=cur_block.getCurrentProbability()*(1-CONSTANTS.ERROR);
            }
            else{
                new_prob=cur_block.getCurrentProbability()*CONSTANTS.ERROR;
            }
            updated_e_prob.add(new_prob);
            normalization_sum+=new_prob;
        }
        for (int i = 0; i <this.totalBlocks ; i++) {
            getBlockList().get(i).currentProbability=(updated_e_prob.get(i)/normalization_sum)*100;
        }
    }

    //------------------------------codes for busting part-----------------------------------

    public void bustghost(int bust_row,int bust_col){
        if (ghost.current_block.getRowNo()==bust_row && ghost.current_block.getColNo()==bust_col){
            ghost.isBusted=true;
            System.out.println("you guessed right");
        }
        else{
            System.out.println("ghost escaped,keep trying");
        }
    }

    public int getRowNo() {
        return totalRowNo;
    }

    public void setRowNo(int totalRowNo) {
        this.totalRowNo = totalRowNo;
    }

    public int getColNo() {
        return totalColNo;
    }

    public void setColNo(int totalColNo) {
        this.totalColNo = totalColNo;
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }

    public void setTotalBlocks(int totalBlocks) {
        this.totalBlocks = totalBlocks;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Block> getBlockList() {
        return blockList;
    }

    public void setBlockList(ArrayList<Block> blockList) {
        this.blockList = blockList;
    }

    public double[][] getGraphArray() {
        return graphArray;
    }

    public void setGraphArray(double[][] graphArray) {
        this.graphArray = graphArray;
    }

    public int getTotalRowNo() {
        return totalRowNo;
    }

    public void setTotalRowNo(int totalRowNo) {
        this.totalRowNo = totalRowNo;
    }

    public int getTotalColNo() {
        return totalColNo;
    }

    public void setTotalColNo(int totalColNo) {
        this.totalColNo = totalColNo;
    }

    public Ghost getGhost() {
        return ghost;
    }

    public void setGhost(Ghost ghost) {
        this.ghost = ghost;
    }

    public ArrayList<Double> getUpdatedTransitionalProb() {
        return updatedTransitionalProb;
    }

    public void setUpdatedTransitionalProb(ArrayList<Double> updatedTransitionalProb) {
        this.updatedTransitionalProb = updatedTransitionalProb;
    }

    public int getMax_manhattan_distance() {
        return max_manhattan_distance;
    }

    public void setMax_manhattan_distance(int max_manhattan_distance) {
        this.max_manhattan_distance = max_manhattan_distance;
    }

    public ArrayList<Double> getUpdated_e_prob() {
        return updated_e_prob;
    }

    public void setUpdated_e_prob(ArrayList<Double> updated_e_prob) {
        this.updated_e_prob = updated_e_prob;
    }

}
