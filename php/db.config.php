<?php

class DatabaseClass{
    private $host = 'localhost';
    private $dbname = 'garageko';
    private $user = 'root';
    private $pass = 'root123';

    private $pdo;

    public function createConnection() {
        try {
            $this->pdo = new PDO("mysql:host=$this->host;dbname=$this->dbname;charset=utf8mb4", $this->user, $this->pass);

            // Set the PDO error mode to exception
            $this->pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            $this->pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);

        } catch (PDOException $e) {
            die("Connection failed: " . $e->getMessage());
        }
    }

    public function testInsert($name) {
        $this->createConnection();
        
        $sql = "INSERT INTO test (Name) VALUES (:name)";
    
        $stmt = $this->pdo->prepare($sql);
    
        $stmt->bindParam(':name', $name);
        $stmt->execute();
    
        $this->pdo = null;
    }

    public function getTests() {
        $this->createConnection();
    
        // SQL query to fetch data
        $sql = "SELECT name AS title FROM test";
    
        try {
            // Prepare the SQL statement
            $stmt = $this->pdo->prepare($sql);
    
            // Execute the statement
            $stmt->execute();
    
            // Fetch all rows as an associative array
            $results = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
            // Return the results as JSON
            echo json_encode($results);
    
        } catch (PDOException $e) {
            // Handle SQL errors
            echo json_encode(array("error" => $e->getMessage()));
        } finally {
            // Close the connection
            $this->pdo = null;
        }
    }
    
}