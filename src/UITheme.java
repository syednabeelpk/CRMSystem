import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * UITheme.java
 *
 * Small shared "design system" for the CRM Swing app: colors, fonts,
 * and a handful of reusable rounded components (cards, buttons, fields).
 * Pure Java Swing — no extra libraries — so the project still compiles
 * and runs exactly like before, it just looks a lot more modern.
 *
 * Used by LoginFrame.java and MainFrame.java.
 */
public class UITheme {

    // ---------- palette ----------
    public static final Color SIDEBAR_BG      = new Color(0x14, 0x1B, 0x32);
    public static final Color SIDEBAR_BG_2    = new Color(0x1C, 0x24, 0x44);
    public static final Color SIDEBAR_ACTIVE  = new Color(0x4F, 0x6E, 0xF7);
    public static final Color SIDEBAR_TEXT    = new Color(0xB8, 0xC0, 0xDA);
    public static final Color SIDEBAR_TEXT_DIM = new Color(0x7A, 0x82, 0xA6);

    public static final Color APP_BG          = new Color(0xF3, 0xF5, 0xFB);
    public static final Color CARD_BG         = Color.WHITE;
    public static final Color BORDER          = new Color(0xE7, 0xEA, 0xF2);

    public static final Color TEXT_DARK       = new Color(0x1A, 0x1F, 0x36);
    public static final Color TEXT_MUTED      = new Color(0x8A, 0x90, 0xA6);

    public static final Color ACCENT_BLUE     = new Color(0x4F, 0x6E, 0xF7);
    public static final Color ACCENT_BLUE_DK  = new Color(0x3D, 0x5A, 0xE0);
    public static final Color ACCENT_GREEN    = new Color(0x22, 0xC5, 0x5E);
    public static final Color ACCENT_ORANGE   = new Color(0xF5, 0x9E, 0x0B);
    public static final Color ACCENT_RED      = new Color(0xEF, 0x44, 0x44);
    public static final Color ACCENT_PURPLE   = new Color(0x8B, 0x5C, 0xF6);

    // ---------- fonts ----------
    public static final String FONT_FAMILY = "Segoe UI";

    public static Font fontBold(int size) {
        return new Font(FONT_FAMILY, Font.BOLD, size);
    }

    public static Font font(int size) {
        return new Font(FONT_FAMILY, Font.PLAIN, size);
    }

    // ======================================================
    // ROUNDED CARD PANEL
    // ======================================================
    public static class Card extends JPanel {
        private int radius = 16;

        public Card() {
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        }

        public Card(int radius) {
            this();
            this.radius = radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(CARD_BG);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.setColor(BORDER);
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ======================================================
    // ROUNDED FILLED BUTTON
    // ======================================================
    public static class RoundButton extends JButton {
        private Color base;
        private Color hover;
        private int radius = 10;

        public RoundButton(String text, Color base) {
            super(text);
            this.base = base;
            this.hover = base.darker();
            setForeground(Color.WHITE);
            setFont(fontBold(13));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isRollover() ? hover : base);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ======================================================
    // ROUNDED OUTLINE BUTTON (secondary actions)
    // ======================================================
    public static class OutlineButton extends JButton {
        private int radius = 10;

        public OutlineButton(String text) {
            super(text);
            setForeground(TEXT_DARK);
            setFont(fontBold(13));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isRollover() ? new Color(0xEC, 0xEF, 0xF7) : new Color(0xF3, 0xF5, 0xFB));
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.setColor(BORDER);
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ======================================================
    // SIDEBAR NAV BUTTON
    // ======================================================
    public static class NavButton extends JButton {
        private boolean active = false;
        private int radius = 10;

        public NavButton(String text) {
            super("  " + text);
            setHorizontalAlignment(SwingConstants.LEFT);
            setFont(font(14));
            setForeground(SIDEBAR_TEXT);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 10));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        public void setActive(boolean active) {
            this.active = active;
            setForeground(active ? Color.WHITE : SIDEBAR_TEXT);
            setFont(active ? fontBold(14) : font(14));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (active) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SIDEBAR_ACTIVE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
                g2.dispose();
            }
            super.paintComponent(g);
        }
    }

    // ======================================================
    // ROUNDED TEXT FIELD BORDER (for JTextField / JPasswordField)
    // ======================================================
    public static class RoundedBorder implements Border {
        private int radius;
        private Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(10, 14, 10, 14);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }
    }

    public static void styleField(JTextField field) {
        field.setBorder(new RoundedBorder(10, BORDER));
        field.setFont(font(14));
        field.setBackground(Color.WHITE);
        field.setCaretColor(TEXT_DARK);
    }

    // ======================================================
    // STAT CARD (used on the Dashboard)
    // ======================================================
    public static Card statCard(String label, String value, String trend, Color accent) {
        Card card = new Card();
        card.setLayout(new BorderLayout(4, 6));

        JLabel labelLbl = new JLabel(label);
        labelLbl.setFont(font(13));
        labelLbl.setForeground(TEXT_MUTED);

        JLabel dot = new JLabel("\u25CF");
        dot.setForeground(accent);
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(labelLbl, BorderLayout.WEST);
        top.add(dot, BorderLayout.EAST);

        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(fontBold(28));
        valueLbl.setForeground(TEXT_DARK);

        JLabel trendLbl = new JLabel(trend);
        trendLbl.setFont(font(12));
        trendLbl.setForeground(ACCENT_GREEN);

        card.add(top, BorderLayout.NORTH);
        card.add(valueLbl, BorderLayout.CENTER);
        card.add(trendLbl, BorderLayout.SOUTH);
        return card;
    }

    // ======================================================
    // TABLE STYLING (applies to any JTable)
    // ======================================================
    public static void styleTable(JTable table) {
        table.setRowHeight(34);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFont(font(13));
        table.setForeground(TEXT_DARK);
        table.setSelectionBackground(new Color(0xEA, 0xEF, 0xFF));
        table.setSelectionForeground(TEXT_DARK);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(fontBold(12));
        header.setForeground(TEXT_MUTED);
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));
        header.setPreferredSize(new Dimension(0, 38));

        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                                                            boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xFA, 0xFB, 0xFD));
                }
                return c;
            }
        });
    }

    // ======================================================
    // SECTION HEADER (page title + subtitle, used at top of each panel)
    // ======================================================
    public static JPanel sectionHeader(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(fontBold(22));
        titleLbl.setForeground(TEXT_DARK);

        JLabel subtitleLbl = new JLabel(subtitle);
        subtitleLbl.setFont(font(13));
        subtitleLbl.setForeground(TEXT_MUTED);

        panel.add(titleLbl);
        panel.add(Box.createVerticalStrut(2));
        panel.add(subtitleLbl);
        return panel;
    }
}
