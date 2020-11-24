package fr.hugosimony.sortingvizualizer;

import java.util.TimerTask;

public class BubbleSort extends TimerTask{

	int i = 0;
	int max = Game.tab.length;
	
	@Override
	public void run() {
		
		try {
			Thread.sleep(Game.speed.getMaximum()+Game.speed.getMinimum()-1-Game.speed.getValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(!isSorted() && !Game.paused) {
			if(i < max - 1) {
				Game.actualColumn = i+1;
	            if (Game.tab[i] <= Game.tab[i + 1])
	            {
	                Game.swap(i,i+1);
	            }
	            if(i == max-2)
                	max--;
	            Game.reloadScreen();
	            i++;            
			}
			else {
				i = 0;
				run();
			}
		}
		else {
			Game.linesSlider.setEnabled(true);
			Game.columnsSlider.setEnabled(true);
			Game.sortButton.setEnabled(true);
			Game.sortButton.setText("Sort");
			this.cancel();
		}
	}
	
	public boolean isSorted() {
		boolean ok = true;
		int i = 0;
		while(ok && i<Game.tab.length-1){
			ok = Game.tab[i] >= Game.tab[i+1];
			i++;
		}
		return ok;
	}

}

