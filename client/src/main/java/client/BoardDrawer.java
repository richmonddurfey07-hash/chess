package client;

import static ui.EscapeSequences.*;

public class BoardDrawer {
    static StringBuilder string = new StringBuilder();

    public static String drawBlack(){
        drawHeaders("black");
        for(int i =8; i>=1; i--){
            string.append(SET_BG_COLOR_LIGHT_GREY);
            for(int j =1; j<=8; j++){
                if (j%2==0){
                    string.append(SET_BG_COLOR_BLACK);
                }
                else{
                    string.append(SET_BG_COLOR_WHITE);
                }
            }
        }
        String board = string.toString();
        return board;
    }

    public static String drawWhite(){
        drawHeaders("white");
        for(int i =1; i<=8; i++){
            string.append(i);
            for(int j =1; j<=8; j++){
                if (j%2==0){
                    string.append(SET_BG_COLOR_BLACK);
                }
                else{
                    string.append(SET_BG_COLOR_WHITE);
                }
            }
        }
        String board = string.toString();
        return board;
    }

    private static void drawHeaders(String color) {
        string.append(SET_BG_COLOR_LIGHT_GREY);
        if (color.equals("white")){
            for(int i = 1; i <=8; i++){
                string.append(i);
            }
        }
        else{
            for (int i = 8; i >=1; i--){
                string.append(i);
            }
        }
    }
}
