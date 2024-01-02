# Rock Paper Scissors Simulator

## Introduction

The Rock Paper Scissors simulator is an elegantly simple yet captivating game. The playing field is initially divided into three equal areas, each populated with rocks, papers, or scissors. Every team comprises an equal number of members, and from the start, all pieces embark on a chaotic journey across the field.

When two pieces collide, a head-to-head battle ensues. Should one piece triumph over the other, the defeated piece joins the winning piece's team. For instance, in the case of a collision between a rock and paper, the rock transforms into a paper and seamlessly continues its journey.

The simulation persists until all pieces align on a single team, ultimately declaring that team as the sole victor. 

Amidst this seemingly barbaric activity, users have the option to wager imaginary currency on the outcome of the tournament.

The engaging dynamics of this simulation, coupled with the strategic choices and unpredictable outcomes, make for an immersive and entertaining experience.

<img src="https://media.giphy.com/media/VIPfTy8y1Lc5iREYDS/giphy-downsized.gif" width="480" height="270"/> 

## More Technical Description

This is a project I did to practice using Java graphics and experiment with OOP. 

In terms of generating odds, my approach involves calculating the expected number of pieces following potential interactions. The probability is then estimated based on the percentage of the total number of pieces represented by a particular team.