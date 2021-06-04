package com.common.base.util;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


public class AliasMethod implements Serializable {
    /* 随机数发生器用于从分布中采样。 */
    //private  Random random;
 
    /* 能力和别名 */
    private  int[] alias;
    private  double[] probability;
 
    /**
     * 构造一种新的从离散分布样本中抽取的别名方法
     * 基于概率分布返回结果。
     * <p/>
     * 作为输入，给出与结果0, 1对应的概率列表,
     * N-1，此构造函数创建概率和别名表
     * 需要有效地从这个分布中取样.
     *
     * PARAM概率的概率列表.
     */
//    public AliasMethod(List<Double> probabilities) {
//        this(probabilities, new Random());
//    }
 
    /**
     * Constructs a new AliasMethod to sample from a discrete distribution and
     * hand back outcomes based on the probability distribution.
     * <p/>
     * Given as input a list of probabilities corresponding to outcomes 0, 1,
     * ..., n - 1, along with the random number generator that should be used
     * as the underlying generator, this constructor creates the probability
     * and alias tables needed to efficiently sample from this distribution.
     *
     * @param probabilities The list of probabilities.
     *
     */
    public AliasMethod(List<Double> probabilities) {
        /* Begin by doing basic structural checks on the inputs. */
        if (probabilities == null)
            throw new NullPointerException();
        if (probabilities.size() == 0)
            throw new IllegalArgumentException("Probability vector must be nonempty.");
 
        /* Allocate space for the probability and alias tables. */
        probability = new double[probabilities.size()];
        alias = new int[probabilities.size()];
 
        /* Store the underlying generator. */
 
        /* Compute the average probability and cache it for later use. */
        final double average = 1.0 / probabilities.size();
 
        /* Make a copy of the probabilities list, since we will be making
         * changes to it.
         */
        probabilities = new ArrayList<Double>(probabilities);
 
        /* Create two stacks to act as worklists as we populate the tables. */
        Deque<Integer> small = new ArrayDeque<Integer>();
        Deque<Integer> large = new ArrayDeque<Integer>();
 
        /* Populate the stacks with the input probabilities. */
        for (int i = 0; i < probabilities.size(); ++i) {
            /* If the probability is below the average probability, then we add
             * it to the small list; otherwise we add it to the large list.
             */
            if (probabilities.get(i) >= average)
                large.add(i);
            else
                small.add(i);
        }
 
        /* As a note: in the mathematical specification of the algorithm, we
         * will always exhaust the small list before the big list.  However,
         * due to floating point inaccuracies, this is not necessarily true.
         * Consequently, this inner loop (which tries to pair small and large
         * elements) will have to check that both lists aren't empty.
         */
        while (!small.isEmpty() && !large.isEmpty()) {
            /* Get the index of the small and the large probabilities. */
            int less = small.removeLast();
            int more = large.removeLast();
 
            /* These probabilities have not yet been scaled up to be such that
             * 1/n is given weight 1.0.  We do this here instead.
             */
            probability[less] = probabilities.get(less) * probabilities.size();
            alias[less] = more;
 
            /* Decrease the probability of the larger one by the appropriate
             * amount.
             */
            probabilities.set(more,
                    (probabilities.get(more) + probabilities.get(less)) - average);
 
            /* If the new probability is less than the average, add it into the
             * small list; otherwise add it to the large list.
             */
            if (probabilities.get(more) >= 1.0 / probabilities.size())
                large.add(more);
            else
                small.add(more);
        }
 
        /* At this point, everything is in one list, which means that the
         * remaining probabilities should all be 1/n.  Based on this, set them
         * appropriately.  Due to numerical issues, we can't be sure which
         * stack will hold the entries, so we empty both.
         */
        while (!small.isEmpty())
            probability[small.removeLast()] = 1.0;
        while (!large.isEmpty())
            probability[large.removeLast()] = 1.0;
    }
 
    /**
     * Samples a value from the underlying distribution.
     *
     * @return A random value sampled from the underlying distribution.
     */
//    public int next() {
//        /* Generate a fair die roll to determine which column to inspect. */
//        int column = random.nextInt(probability.length);
//
//        /* Generate a biased coin toss to determine which option to pick. */
//        boolean coinToss = random.nextDouble() < probability[column];
//
//        /* Based on the outcome, return either the column or its alias. */
//       /* Log.i("1234","column="+column);
//        Log.i("1234","coinToss="+coinToss);
//        Log.i("1234","alias[column]="+coinToss);*/
//        return coinToss ? column : alias[column];
//    }
    public AliasMethod(){}

//    public Random getRandom() {
//        return random;
//    }

    public int[] getAlias() {
        return alias;
    }

    public double[] getProbability() {
        return probability;
    }

//    public void setRandom(Random random) {
//        this.random = random;
//    }

    public void setAlias(int[] alias) {
        this.alias = alias;
    }

    public void setProbability(double[] probability) {
        this.probability = probability;
    }
}