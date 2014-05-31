package extracter.card.prob;

import java.util.LinkedList;

import extracter.Constants;
import extracter.card.Card;

public class ProbList{
	LinkedList<ProbListItem> items;
	double probMax;
	double probMin;
	public ProbList()
	{
		items = new LinkedList<ProbListItem>();
		probMax = 100;
	}

	public void add(Card card, double prob)
	{
		int index = 0;
		int i;
		for(i=0;i<items.size();i++)
		{
			if(prob > items.get(i).getProb())
			{		
				break;
			}
		}
		index = i;
		if(items.size() < Constants.probItemLimit)
		{
			items.add(index,new ProbListItem(card, prob));
		}
		else
		{
			items.add(index, new ProbListItem(card, prob));
			items.removeFirst();
		}
		probMax = items.get(0).getProb();
		probMin = items.get(items.size()-1).getProb();
	/*	System.out.println("Possible Card List Start");
		for(ProbListItem y : items)
			System.out.println(y.getCard().getName() + " " +  y.getProb());*/
	}

	public LinkedList<ProbListItem> getItems() {
		return items;
	}

	public void setItems(LinkedList<ProbListItem> items) {
		this.items = items;
	}

	public double getProbMax() {
		return probMax;
	}

	public void setProbMax(double probMax) {
		this.probMax = probMax;
	}

	public double getProbMin() {
		return probMin;
	}

	public void setProbMin(double probMin) {
		this.probMin = probMin;
	}
}
