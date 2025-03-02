package org.example;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class RecommendationSystem {
    public static void main(String[] args) {
        try {
            // Load sample user-item ratings from a CSV file
            DataModel model = new FileDataModel(new File("data.csv"));

            // Compute similarity between users
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Define user neighborhood
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Create a user-based recommender
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Get recommendations for a specific user (e.g., user ID = 1)
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);

            // Print recommendations
            System.out.println("Recommendations for user 1:");
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Item: " + recommendation.getItemID() + " | Score: " + recommendation.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
