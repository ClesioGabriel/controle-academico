package br.unimontes.ccet.dcc.pg1.view.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public final class Theme {

    private Theme() {}

    // Palette
    public static final Color PRIMARY = new Color(59, 130, 246); // #3B82F6
    public static final Color SECONDARY = new Color(107, 114, 128); // #6B7280
    public static final Color BACKGROUND = new Color(249, 250, 251); // #F9FAFB
    public static final Color TEXT = new Color(17, 24, 39); // #111827
    public static final Color TEXT_SECONDARY = new Color(55, 65, 81); // #374151

    // Feedback
    public static final Color SUCCESS = new Color(16, 185, 129); // #10B981
    public static final Color DANGER = new Color(239, 68, 68); // #EF4444
    public static final Color WARNING = new Color(245, 158, 11); // #F59E0B

    // Spacing and radii
    public static final int RADIUS = 8;
    public static final int GAP_XS = 8;
    public static final int GAP_SM = 12;
    public static final int GAP_MD = 16;
    public static final int GAP_LG = 24;

    // Fonts (attempts; system fallback if not installed)
    public static Font h1(float size) { return new Font("Inter", Font.BOLD, Math.round(size)); }
    public static Font h2(float size) { return new Font("Inter", Font.BOLD, Math.round(size)); }
    public static Font body(float size) { return new Font("Inter", Font.PLAIN, Math.round(size)); }

    // Buttons
    public static void stylePrimaryButton(JButton b) {
        b.setBackground(PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFont(body(14));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        b.setMargin(new java.awt.Insets(6,12,6,12));
        b.setMinimumSize(new Dimension(100, 40));
        b.setOpaque(true);
    }

    public static void styleSecondaryButton(JButton b) {
        b.setBackground(BACKGROUND);
        b.setForeground(TEXT);
        b.setFont(body(14));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createLineBorder(new Color(229,231,235), 1));
        b.setMargin(new java.awt.Insets(6,12,6,12));
        b.setMinimumSize(new Dimension(100, 40));
        b.setOpaque(true);
    }

    // Panels / Cards
    public static void styleCard(JPanel p) {
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229,231,235), 1),
                BorderFactory.createEmptyBorder(GAP_MD, GAP_MD, GAP_MD, GAP_MD)));
    }

    public static void styleHeader(JLabel l) {
        l.setOpaque(true);
        l.setBackground(BACKGROUND);
        l.setForeground(TEXT);
        l.setFont(h2(20));
        l.setBorder(BorderFactory.createEmptyBorder(GAP_SM, GAP_MD, GAP_SM, GAP_MD));
    }

    public static void styleStatusBar(JLabel l) {
        l.setOpaque(true);
        l.setBackground(new Color(244,246,248));
        l.setForeground(TEXT_SECONDARY);
        l.setFont(body(13));
        l.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    // Tables
    public static void styleTable(JTable t) {
        t.setShowGrid(true);
        t.setGridColor(new Color(243, 244, 246));
        t.setIntercellSpacing(new Dimension(0, 0));
        t.setRowHeight(30);
        t.setFont(body(14));
        t.setForeground(TEXT);
        t.setBackground(Color.WHITE);
        JTableHeader h = t.getTableHeader();
        if (h != null) {
            h.setFont(body(13).deriveFont(Font.BOLD));
            h.setForeground(Color.WHITE);
            h.setBackground(new Color(55,65,81)); // #374151
        }
        // Zebra rows renderer
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(229, 246, 255));
                } else {
                    if (row % 2 == 0) c.setBackground(Color.WHITE);
                    else c.setBackground(new Color(249, 250, 251));
                }
                setBorder(BorderFactory.createEmptyBorder(4,8,4,8));
                return c;
            }
        };
        t.setDefaultRenderer(Object.class, renderer);
    }
}
