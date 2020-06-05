import java.awt.*;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.util.Random;
import java.util.Timer;

public class Pong extends Canvas {
	Point delta;
	// Point alpha;
	Ellipse2D.Double ball;
	Rectangle paddle1;
	Rectangle paddle2;
	int p1Score;
	int p2Score;
	Graphics gfx;
	Image img;
	int rand1;
	int rand2;

	public static void main(String[] args) {
		JFrame win = new JFrame("Pong");
		win.setSize(1010, 735);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.add(new Pong());
		win.setVisible(true);
	}

	public Pong() {
		enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK);
		requestFocus();

		ball = new Ellipse2D.Double(500, 350, 20, 20);
		delta = new Point(-5, 5);
		// alpha = new Point(5, -5);
		paddle1 = new Rectangle(50, 250, 20, 200);
		paddle2 = new Rectangle(925, 50, 20, 200);
		p1Score = p2Score = 0;

		rand1 = (int) (Math.random() * 500) + 100;
		rand2 = (int) (Math.random() * 500) + 100;

		Timer t = new Timer(true);
		t.schedule(new java.util.TimerTask() {
			public void run() {
				doStuff();
				repaint();
			}
		}, 10, 10);

	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.black);
		g2.fill(ball);

		g2.setColor(Color.blue);
		g2.fill(paddle1);

		g2.setColor(Color.red);
		g2.fill(paddle2);

		g2.setColor(Color.black);
		g2.setFont(new Font("Arial", 1, 50));
		g2.drawString("" + p1Score, 400, 50);

		g2.setColor(Color.black);
		g2.setFont(new Font("Arial", 1, 50));
		g2.drawString("" + p2Score, 600, 50);

	}

	// public void draw(Graphics g) {
	// Graphics2D g1 = (Graphics2D) g;

	// g1.setColor(Color.green);
	// g1.drawString("" + p1Score, 100, 100);
	// g1.drawString("" + p1Score, 300, 100);

	// }

	public void processKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				paddle1.y -= 15;
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				paddle1.y += 15;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				paddle2.y -= 15;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				paddle2.y += 15;
			}
		}
	}

	public void doStuff() {
		ball.x += delta.x;
		ball.y += delta.y;

		// and bounce if we hit a wall
		if (ball.y < 0 || ball.y + 20 > 700)
			delta.y = -delta.y;
		if (ball.x < 0 || ball.x > 1000)
			delta.x = -delta.x;

		// check if the ball is hitting the paddle
		if (ball.intersects(paddle1))
			delta.x = -delta.x;

		if (ball.intersects(paddle2))
			delta.x = -delta.x;

		// check for scoring
		if (ball.x > 1000) {
			ball.x = 700;
			ball.y = 350;
			delta = new Point(-5, 5);
			p1Score++;
		}

		if (ball.x < 0) {
			ball.x = 300;
			ball.y = 350;
			delta = new Point(5, -5);
			p2Score++;

		}

	}

	public boolean isFocusable() {
		return true;
	}
}