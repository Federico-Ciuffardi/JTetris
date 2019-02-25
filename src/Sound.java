import javax.sound.sampled.*;
	public class Sound {
		private Clip clip;
		
		public static Sound fall = new Sound("block_hits_bottom.wav");
		public static Sound clearLine = new Sound("clear_line_with_special_block.wav");
		public static Sound rotate = new Sound("rotate_block.wav");
		public static Sound background = new Sound("Tetris.wav");
		public static void resetSounds() {
			fall = new Sound("block_hits_bottom.wav");
			clearLine = new Sound("clear_line_with_special_block.wav");
			rotate = new Sound("rotate_block.wav");
			background = new Sound("Tetris.wav");
		}
		public Sound (String fileName) {
			try {
				AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
				AudioFormat format = ais.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				clip = (Clip)AudioSystem.getLine(info);;
				clip.open(ais);
			} catch (Exception e) {e.printStackTrace();}
		}
		public void play() {
			try {
				if (clip != null) {
					new Thread() {
						public void run() {
							synchronized (clip) {
								clip.stop();
								clip.setFramePosition(0);
								clip.start();
							}
						}
					}.start();
				}
			} catch (Exception e) {e.printStackTrace();}
		}
		public void stop(){
			if(clip == null) return;
				clip.stop();
			}
		public void loop() {
			try {
				if (clip != null) {
					new Thread() {
						public void run() {
							synchronized (clip) {
								clip.stop();
								clip.setFramePosition(0);
								clip.loop(Clip.LOOP_CONTINUOUSLY);
							}
						}
					}.start();
				}
			} catch (Exception e) {e.printStackTrace();}
		}
		public boolean isActive(){
			return clip.isActive();
		}
}
