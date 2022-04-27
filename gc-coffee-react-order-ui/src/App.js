import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import React, {useState} from 'react';
import {ProductList} from "./components/ProductList";
import {Summary} from "./components/Summary";

function App() {
    const [products, setProducts] = useState([
        {productId: 'uuid-1', productName: '콜롬비아 커피 1', category: '커피빈', price: 5000 },
        {productId: 'uuid-2', productName: '콜롬비아 커피 2', category: '커피빈', price: 5000 },
        {productId: 'uuid-3', productName: '콜롬비아 커피 3', category: '커피빈', price: 5000 },
    ]);

    const [items, setItems] = useState([]);

    return (
        <div className="container-fluid">
            <div className="row justify-content-center m-4">
                <h1 className="text-center">Grids & Circle</h1>
            </div>
            <div className="card">
                <div className="row">
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <ProductList products={products}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <Summary items={items}/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
