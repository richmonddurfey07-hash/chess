package client;

import java.util.List;

import static ui.EscapeSequences.*;

public class BoardDrawer {

    public static String drawBlack(){
        StringBuilder string = new StringBuilder();
        drawHeaders("black", string);
        for(int i =1; i<=8; i++){
            string.append(SET_BG_COLOR_LIGHT_GREY).append(" ").append(i).append(" ");
            for(int j =8; j<=1; j--){
                if ((j+i)%2==0){
                    string.append(SET_BG_COLOR_BLACK).append(EMPTY);
                }
                else{
                    string.append(SET_BG_COLOR_WHITE).append(EMPTY);
                }
            }
            string.append(SET_BG_COLOR_LIGHT_GREY).append(" ").append(i).append(" ")
                    .append(RESET_BG_COLOR).append("\n");
        }
        drawHeaders("black",string);
        String board = string.toString();
        return board;
    }

    public static String drawWhite(){
        StringBuilder string = new StringBuilder();
        drawHeaders("white",string);
        for(int i =8; i>=1; i--){
            string.append(SET_BG_COLOR_LIGHT_GREY).append(" ").append(i).append(" ");
            for(int j =1; j<=8; j++){
                if ((j+i)%2==0){
                    string.append(SET_BG_COLOR_BLACK).append(EMPTY);
                }
                else{
                    string.append(SET_BG_COLOR_WHITE).append(EMPTY);
                }
            }
            string.append(SET_BG_COLOR_LIGHT_GREY).append(" ").append(i).append(" ")
                    .append(RESET_BG_COLOR).append("\n");
        }
        drawHeaders("white",string);
        String board = string.toString();
        return board;
    }

    private static void drawHeaders(String color,StringBuilder string) {
        String[] head = {"  ", " a "," b "," c "," d "," e "," f "," g "," h ", "  "};
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
