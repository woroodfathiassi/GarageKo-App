<?php

echo "assi";

include 'db.config.php';

// // Create an instance of DatabaseClass and call its methods
try {
    $db = new DatabaseClass();
    $db->getTests();
} catch (Exception $e) {
    echo 'Error: ' . $e->getMessage();
}




