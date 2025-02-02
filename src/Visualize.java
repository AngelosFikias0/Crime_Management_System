//GUI class using JUNG to visualize the criminals' network
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

@SuppressWarnings("serial")
public class Visualize extends JFrame {

    private JPanel panel = new JPanel(); 
    private Graph<Suspect, String> graph; // Graph to store the suspects and their relationships
    private VisualizationViewer<Suspect, String> vv; // Visualization viewer to display the graph
    private JLabel diameterLabel = new JLabel("Diameter: Not Calculated"); // Label to display the graph's diameter

    public Visualize(Registry registry) {
    	// Create an empty graph
        graph = new UndirectedSparseGraph<>();  

        // Add vertices (suspects) to the graph
        for(Suspect s : registry.getSuspects()) {
            graph.addVertex(s);
        }
        
        // Add edges between suspects if they are partners
        for (Suspect s : registry.getSuspects()) {
            for (Suspect partner : s.getPartners()) {
                if (!graph.isSuccessor(s, partner)) {
                    graph.addEdge(s.getCodeName() + "-" + partner.getCodeName(), s, partner);
                }
            }
        }

        // Calculate the diameter of the graph
        double diameter = calculateGraphDiameter(graph);
        diameterLabel.setText("Diameter: " + diameter); 
        
        // Create a layout for the graph visualization
        Layout<Suspect, String> layout = new FRLayout<>(graph);
        layout.setSize(new Dimension(500, 400)); // Set the preferred size for the layout
        vv = new VisualizationViewer<>(layout);

        // Set the label transformer to display the suspect's code name
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Suspect>() {
            @Override
            public String transform(Suspect suspect) {
                return suspect.getCodeName(); 
            }
        });

        vv.setPreferredSize(new Dimension(600, 500)); // Set the size of the visualization

        panel.setLayout(new BorderLayout());
        panel.add(vv, BorderLayout.CENTER); // Add the graph visualization
        panel.add(diameterLabel, BorderLayout.SOUTH); // Add the diameter label at the bottom

        this.setTitle("Graph Visualization");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(panel);
        this.pack(); // Adjust the window size to fit its components
        this.setVisible(true); 
    }

    // Method to calculate the diameter of the graph
    private double calculateGraphDiameter(Graph<Suspect, String> graph) {
        DijkstraDistance<Suspect, String> dijkstra = new DijkstraDistance<>(graph); // Initialize Dijkstra's algorithm to find shortest paths

        double diameter = 0.0; // Start with a diameter of 0.0
        // Compare each pair of vertices in the graph
        for (Suspect vertex1 : graph.getVertices()) {
            for (Suspect vertex2 : graph.getVertices()) {
                if (!vertex1.equals(vertex2)) { // Skip comparing a vertex with itself
                    Double distance = (Double) dijkstra.getDistance(vertex1, vertex2); // Get the distance between the two vertices
                    if (distance != null && distance > diameter) { // Update the diameter if this distance is larger
                        diameter = distance; // Set the new diameter
                    }
                }
            }
        }
        return diameter; 
    }
}