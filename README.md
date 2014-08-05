Basically, what this does is that gets deck images as input and returns ```Deck``` Object with the all the information a deck has. Since OSx and Windows uses different image scrapping tools, you can put a code in front of this part to make it viable.

### How to use it?

To export a deck, one need to call ```ExtracterMain.exportDeck()``` function. Here is the pseudo-code:

1. Make sure users deck page in hearthstone is open and visible.
2. Get image of the window(img1)
3. Scroll deck with Robot class
4. Get image of the window(img2- scrolled image of the deck)
5. Call ExtracterMain.exportDeck(img1,img2)

I added a simple ```main()``` funtion to ```ExtracterMain.java``` which basically does above pseudo-code. Try and run it! There is also a jar file which you can download and use it.

### How does it work?

In ```zulu.deckport.txt``` package you will see two txt files. ```cards.txt``` for trained data set for each card on Hearthstone([except these two](https://github.com/shyos/deckport/issues/3)) and ```cardcounts.txt``` for trained data set for card counts 2,3 and 4(above does not trained,yet).

Deck Export uses these information and finds most similar cards for each undefined ```Card``` in deck. Then builds ```Deck``` object with using these similarity ratings.

### Trademark

This library designed for HearthStats. This is also what I used in DeckPort. If anyone copies code or uses jar let me know you are using it, then we are okay! :) 

### Updates
Does not includes Naxxramas cards.
