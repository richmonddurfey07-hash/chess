package client;

import static ui.EscapeSequences.*;

public class BoardDrawer {

    public static String drawBlack(){
        StringBuilder string = new StringBuilder();
        drawHeaders("black", string);
        String[] pieces = {"R","N","B","K","Q","B","N","R"};
        for(int i =1; i<=8; i++){
            string.append(SET_BG_COLOR_LIGHT_GREY).append(" ").append(SET_TEXT_COLOR_BLACK).append(i).append(" ");
            for(int j =8; j>=1; j--){
                drawBoard(string,i,j, pieces);
            }
            string.append(SET_BG_COLOR_LIGHT_GREY).append(" ").append(i).append(SET_TEXT_COLOR_BLACK).append(" ")
                    .append(RESET_BG_COLOR).append("\n");
        }
        drawHeaders("black",string);
        String board = string.toString();
        return board;
    }

    private static void drawBoard(StringBuilder string, int i, int j, String[] pieces) {
        if ((j+i)%2==0){
            string.append(SET_BG_COLOR_BLACK);
        }
        else{
            string.append(SET_BG_COLOR_WHITE);
        }
        if (i==1){
            string.append(SET_TEXT_COLOR_BLUE).append(" ").append(pieces[j-1]).append(" ");
        }
        else if (i==2){
            string.append(SET_TEXT_COLOR_BLUE).append(" ").append("P").append(" ");
        }
        else if(i==7){
            string.append(SET_TEXT_COLOR_RED).append(" ").append("P").append(" ");
        }
        else if (i==8){
            string.append(SET_TEXT_COLOR_RED).append(" ").append(pieces[j-1]).append(" ");
        }
        else{
            emptySquares(string,i,j);
        }
    }

    private static void emptySquares(StringBuilder string, int i, int j) {
        if ((j+i)%2==0){
            string.append(SET_BG_COLOR_BLACK).append("   ");
        }
        else{
            string.append(SET_BG_COLOR_WHITE).append("   ");
        }
    }

    public static String drawWhite(){
        StringBuilder string = new StringBuilder();
        String[] pieces = {"R","N","B","K","Q","B","N","R"};
        drawHeaders("white",string);
        for(int i =8; i>=1; i--){
            string.append(SET_BG_COLOR_LIGHT_GREY).append(" ").append(SET_TEXT_COLOR_BLACK).append(i).append(" ");
            for(int j =1; j<=8; j++){
                drawBoard(string,i,j,pieces);
            }
            string.append(SET_BG_COLOR_LIGHT_GREY).append(" ").append(SET_TEXT_COLOR_BLACK).append(i).append(" ")
                    .append(RESET_BG_COLOR).append("\n");
        }
        drawHeaders("white",string);
        String board = string.toString();
        return board;
    }

    private static void drawHeaders(String color,StringBuilder string) {
        String[] head = {" ", "a","b","c","d","e","f","g","h", " "};
        string.append(SET_BG_COLOR_LIGHT_GREY);
        if (color.equals("white")){
            for(int i = 0; i <=9; i++){
                string.append(SET_TEXT_COLOR_BLACK).append(" ").append(head[i]).append(" ");
            }
        }
        else{
            for (int i = 9; i >=0; i--){
                string.append(SET_TEXT_COLOR_BLACK).append(" ").append(head[i]).append(" ");
            }
        }
        string.append(RESET_BG_COLOR).append("\n");
    }
}
