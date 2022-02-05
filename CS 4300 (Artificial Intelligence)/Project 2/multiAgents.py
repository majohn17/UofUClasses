# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        temp = successorGameState.getScore()
        # Get manhattan distance to ghosts and find lowest distance
        bestGhost = float('Inf')
        for x in newGhostStates:
            ghost = util.manhattanDistance(newPos, x.getPosition())
            if ghost < bestGhost:
                bestGhost = ghost
        # Ghost is very close
        if bestGhost < 2:
            return float('-inf')
        # Get manhattan distance to food and find lowest distance
        bestFood = float('inf')
        for y in newFood.asList():
            food = util.manhattanDistance(newPos, y)
            if food < bestFood:
                bestFood = food
        # Update current score with score + the new values we obtained
        temp = temp + (bestGhost/bestFood)
        return temp;


def scoreEvaluationFunction(currentGameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        def maximum(gameState, depth):
            if gameState.isLose() or gameState.isWin() or depth == 0:
                return self.evaluationFunction(gameState)
            best = float('-Inf')
            for action in gameState.getLegalActions(0):
                best = max(best, minimum(gameState.generateSuccessor(0, action), depth, 1))
            return best

        def minimum(gameState, depth, agents):
            if gameState.isLose() or gameState.isWin():
                return self.evaluationFunction(gameState)
            best = float('Inf')
            for action in gameState.getLegalActions(agents):
                best = min(best, maximum(gameState.generateSuccessor(agents, action), depth-1)) if agents == gameState.getNumAgents() - 1 else min(best, minimum(gameState.generateSuccessor(agents, action), depth, agents+1))
            return best

        def minimax(gameState):
            val = float('-Inf')
            for next in gameState.getLegalActions(0):
                temp = minimum(gameState.generateSuccessor(0, next), self.depth, 1)
                if temp > val:
                    val = temp
                    result = next
            return result

        return minimax(gameState)

class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        def alphabeta(gameState, depth, agents, alpha, beta):
            # No legal actions left or max depth
            if not gameState.getLegalActions(agents) or depth == self.depth:
                return self.evaluationFunction(gameState),0
            # Get next ghost or pacman
            next = self.index if agents == gameState.getNumAgents() - 1 else agents + 1
            # Increase depth if level complete
            if agents == gameState.getNumAgents() - 1:
                depth = depth + 1

            # Find minmax values
            temp = []
            for action in gameState.getLegalActions(agents):
                if not temp:
                    val = alphabeta(gameState.generateSuccessor(agents,action), depth, next, alpha, beta)
                    temp.append(val[0])
                    temp.append(action)

                    if agents == self.index:
                        alpha = max(temp[0], alpha)
                    else:
                        beta = min(temp[0], beta)
                else:
                    if temp[0] < alpha and agents != self.index or temp[0] > beta and agents == self.index:
                        return temp

                    prev = temp[0]
                    val = alphabeta(gameState.generateSuccessor(agents,action), depth, next, alpha, beta)

                    if agents == self.index and val[0] > prev:
                        alpha = max(val[0], alpha)
                        temp[0] = val[0]
                        temp[1] = action  
                    if agents != self.index and val[0] < prev:
                        beta = min(val[0], beta)
                        temp[0] = val[0]
                        temp[1] = action
            return temp

        return alphabeta(gameState, 0, self.index, float('-Inf'), float('Inf'))[1]
        

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        def expectimax(state, depth, agents):
            # Pacman
            if agents == state.getNumAgents():
                return expectimax(state, depth + 1, 0)

            if state.getLegalActions(agents) == 0 or state.isWin() or state.isLose() or depth == self.depth:
                return self.evaluationFunction(state)

            # Get all successors
            average = []
            for action in state.getLegalActions(agents):
                average.append(expectimax(state.generateSuccessor(agents, action), depth, agents + 1))

            # Find best move
            if agents % state.getNumAgents() == 0:
                return max(average)

            # Random average from legal moves
            else:
                return sum(average)/len(average)

        return max(gameState.getLegalActions(0), key = lambda temp: expectimax(gameState.generateSuccessor(0, temp), 0, 1))


def betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: Same evaluation as normal evaluation but incorporates ghosts being scared of power pellet pacman
    """
    bestGhost = float('Inf')
    for x in currentGameState.getGhostStates():
        ghost = util.manhattanDistance(currentGameState.getPacmanPosition(), x.getPosition())
        if ghost < bestGhost:
            bestGhost = ghost

    bestFood = float('Inf')
    for y in currentGameState.getFood().asList():
        food = util.manhattanDistance(currentGameState.getPacmanPosition(), y)
        if food < bestFood:
            bestFood = food

    scared = [ghostState.scaredTimer for ghostState in currentGameState.getGhostStates()]
    normalEval = currentGameState.getScore() + (bestGhost/bestFood)
    return normalEval if scared == 0 else normalEval + sum(scared)
    
# Abbreviation
better = betterEvaluationFunction
