package src.button;

public class MenuButton {
    private String text;
    private int x, y, width, height;
    private boolean isHovered;

    public MenuButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isHovered = false;
    }

    public void draw(java.awt.Graphics g) {
        if (isHovered) {
            g.setColor(java.awt.Color.LIGHT_GRAY);
        } else {
            g.setColor(java.awt.Color.GRAY);
        }
        g.fillRect(x, y, width, height);
        g.setColor(java.awt.Color.BLACK);
        g.drawString(text, x + 10, y + 20);
    }
}
