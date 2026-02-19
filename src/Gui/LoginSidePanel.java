package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LoginSidePanel extends JPanel {
    
    private JLayeredPane layered;
    private JLabel imageLabel;
    private JPanel textPanel;
    private JPanel overlayPanel;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel lblWelcome;
    private JLabel lblDesc;
    private Image originalImage;
    
    // Default padding values
    private int paddingTop = 60;
    private int paddingLeft = 60;
    private int paddingBottom = 40;
    private int paddingRight = 60;
    
    public LoginSidePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(11, 15, 20));
        
        // ================= LOAD IMAGE =================
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/img/shine.jpg"));
            originalImage = icon.getImage();
        } catch (Exception e) {
            System.out.println("Image not found! Using fallback gradient.");
            originalImage = null;
        }
        
        // ================= LAYERED PANE =================
        layered = new JLayeredPane();
        layered.setLayout(null);
        
        // ================= IMAGE LABEL =================
        imageLabel = new JLabel();
        imageLabel.setBounds(0, 0, getWidth(), getHeight());
        
        // ================= OVERLAY PANEL (with dark tint) =================
        overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 160));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        overlayPanel.setOpaque(false);
        overlayPanel.setBounds(0, 0, 400, 400);
        overlayPanel.setLayout(new BorderLayout());
        overlayPanel.setBorder(BorderFactory.createEmptyBorder(paddingTop, paddingLeft, paddingBottom, paddingRight));
        
        // ================= TEXT PANEL =================
        textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        
        // ================= TITLE =================
        lblTitle = new JLabel("CyberTech-Login");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(34, 197, 94));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(lblTitle);
        
        textPanel.add(Box.createVerticalStrut(15));
        
        // ================= SUBTITLE =================
        lblSubtitle = new JLabel();
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitle.setForeground(new Color(209, 213, 219));
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        updateSubtitleText();
        textPanel.add(lblSubtitle);
        
        textPanel.add(Box.createVerticalStrut(30));
        
        // ================= WELCOME MESSAGE =================
        lblWelcome = new JLabel("Hi, We're CyberTech");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(lblWelcome);
        
        textPanel.add(Box.createVerticalStrut(15));
        
        // ================= DESCRIPTION =================
        lblDesc = new JLabel();
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblDesc.setForeground(new Color(156, 163, 175));
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        updateDescriptionText();
        textPanel.add(lblDesc);
        
        // Add vertical glue to push content to top (default position)
        textPanel.add(Box.createVerticalGlue());
        
        // ================= ADD TEXT PANEL TO OVERLAY =================
        overlayPanel.add(textPanel, BorderLayout.CENTER);
        
        // ================= ADD TO LAYERED PANE =================
        layered.add(imageLabel, Integer.valueOf(0));
        layered.add(overlayPanel, Integer.valueOf(1));
        
        add(layered, BorderLayout.CENTER);
        
        // ================= RESPONSIVE LISTENER =================
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
            
            @Override
            public void componentShown(ComponentEvent e) {
                resizeComponents();
            }
        });
    }
    
    private void resizeComponents() {
        int width = getWidth();
        int height = getHeight();
        
        // Update layered pane bounds
        layered.setBounds(0, 0, width, height);
        
        // Scale image to fit panel
        if (originalImage != null) {
            Image scaled = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setIcon(null);
            imageLabel.setOpaque(true);
            imageLabel.setBackground(new Color(17, 24, 39));
        }
        
        // Update component bounds
        imageLabel.setBounds(0, 0, width, height);
        
        // Update overlay panel bounds
        overlayPanel.setBounds(0, 0, width, height);
        
        // Adjust padding and font sizes based on panel width
        adjustResponsiveLayout(width);
        
        layered.revalidate();
        layered.repaint();
    }
    
    private void adjustResponsiveLayout(int width) {
        // Adjust border padding based on screen size
        if (width < 500) {
            setPadding(30, 30, 20, 30);
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
            lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
            lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        } else if (width < 700) {
            setPadding(50, 50, 30, 50);
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
            lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
            lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        } else {
            setPadding(70, 70, 40, 70);
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
            lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
            lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        }
        
        // Update subtitle text based on width
        updateSubtitleText();
        
        // Update description text
        updateDescriptionText();
    }
    
    private void updateSubtitleText() {
        int width = getWidth();
        String text;
        
        if (width < 500) {
            text = "Sign in to your account";
        } else if (width < 700) {
            text = "Sign in to access your dashboard";
        } else {
            text = "Sign in to access your personalized dashboard";
        }
        
        lblSubtitle.setText(text);
    }
    
    private void updateDescriptionText() {
        int width = getWidth();
        String descWidth = width < 500 ? "250px" : (width < 700 ? "280px" : "320px");
        lblDesc.setText("<html><div style='width:" + descWidth + ";'>"
                + "Enter your credentials to access your personalized dashboard "
                + "and manage your shopping cart."
                + "</div></html>");
    }
    
    /**
     * Sets the content position within the panel
     * @param position "top", "center", or "bottom"
     */
    public void setContentPosition(String position) {
        textPanel.removeAll();
        
        // Re-add components
        textPanel.add(lblTitle);
        textPanel.add(Box.createVerticalStrut(15));
        textPanel.add(lblSubtitle);
        textPanel.add(Box.createVerticalStrut(30));
        textPanel.add(lblWelcome);
        textPanel.add(Box.createVerticalStrut(15));
        textPanel.add(lblDesc);
        
        switch (position.toLowerCase()) {
            case "top":
                // No top glue, bottom glue pushes nothing
                textPanel.add(Box.createVerticalGlue());
                setPadding(40, paddingLeft, 20, paddingRight);
                break;
                
            case "bottom":
                // Top glue pushes content down, no bottom glue
                textPanel.add(Box.createVerticalGlue(), 0);
                setPadding(20, paddingLeft, 40, paddingRight);
                break;
                
            case "center":
            default:
                // Both top and bottom glue for centering
                textPanel.add(Box.createVerticalGlue(), 0);
                textPanel.add(Box.createVerticalGlue());
                setPadding(40, paddingLeft, 40, paddingRight);
                break;
        }
        
        textPanel.revalidate();
        textPanel.repaint();
    }
    
    /**
     * Sets the horizontal alignment of all text components
     * @param alignment Component.LEFT_ALIGNMENT, Component.CENTER_ALIGNMENT, or Component.RIGHT_ALIGNMENT
     */
    public void setTextAlignment(float alignment) {
        lblTitle.setAlignmentX(alignment);
        lblSubtitle.setAlignmentX(alignment);
        lblWelcome.setAlignmentX(alignment);
        lblDesc.setAlignmentX(alignment);
        
        // Adjust border padding based on alignment
        if (alignment == Component.LEFT_ALIGNMENT) {
            setPadding(paddingTop, 70, paddingBottom, 40);
        } else if (alignment == Component.RIGHT_ALIGNMENT) {
            setPadding(paddingTop, 40, paddingBottom, 70);
        } else {
            setPadding(paddingTop, 60, paddingBottom, 60);
        }
        
        textPanel.revalidate();
        textPanel.repaint();
    }
    
    /**
     * Sets custom padding for the overlay panel
     * @param top top padding
     * @param left left padding
     * @param bottom bottom padding
     * @param right right padding
     */
    public void setPadding(int top, int left, int bottom, int right) {
        this.paddingTop = top;
        this.paddingLeft = left;
        this.paddingBottom = bottom;
        this.paddingRight = right;
        overlayPanel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        revalidate();
        repaint();
    }
    
    /**
     * Updates the welcome message
     * @param message new welcome message
     */
    public void setWelcomeMessage(String message) {
        lblWelcome.setText(message);
    }
    
    /**
     * Updates the description text
     * @param description new description text
     */
    public void setDescription(String description) {
        int width = getWidth();
        String descWidth = width < 500 ? "250px" : (width < 700 ? "280px" : "320px");
        lblDesc.setText("<html><div style='width:" + descWidth + ";'>" + description + "</div></html>");
    }
    
    /**
     * Gets the current padding values
     * @return array of [top, left, bottom, right]
     */
    public int[] getPadding() {
        return new int[]{paddingTop, paddingLeft, paddingBottom, paddingRight};
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Ensure initial sizing
        if (getWidth() > 0 && getHeight() > 0 && imageLabel.getIcon() == null && originalImage != null) {
            resizeComponents();
        }
    }
}