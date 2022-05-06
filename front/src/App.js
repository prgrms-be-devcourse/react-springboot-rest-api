import './App.css';
import 'bootstrap/dist/css/bootstrap.css';

import React, {useEffect, useState} from 'react';
import {ProductList} from "./components/ProductList";
import {Summary} from "./components/Summary";
import axios from "axios";

function App() {
  const [products, setProducts] = useState([]);

  const [items, setItems] = useState([]);

  const handleAddClicked = productId => {
    const product = products.find(v => v.productId === productId);
    const found = items.find(v => v.productId === productId);
    const updateItems =
        found ? items.map(v => (v.productId === productId) ? {
          ...v,
          count: v.count + 1
        } : v) : [...items, {...product, count: 1}];
    setItems(updateItems);
  }
  useEffect(() => {
    axios.get('http://localhost:8080/api/v1/products')
        .then(v => setProducts(v.data))
  }, [])
  const handleOrderSubmit = (order) => {
    if (items.length === 0) {
      alert("아이템을 추가해 주세요")
    } else {
      axios.post("http://localhost:8080/api/v1/orders", {
        email: order.email,
        address: order.address,
        postcode: order.postcode,
        orderItems: items.map(v => ({
          productId: v.productId,
          category: v.category,
          price: v.price,
          count: v.count
        }))
      }).then(
          v => alert("주문이 정상적으로 접수되었습니다."),
          e => {
            alert("서버 장애");
            console.error(e);
          })
    }
  }
  return (
      <div className="container-fluid">
        <div className="row justify-content-center m-4 bg-dark font-monospace text-warning">
          <h1 className="text-center">음료 주문</h1>
        </div>
        <div className="card">
          <div className="row">
            <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
              <ProductList products={products} onAddClick={handleAddClicked}/>
            </div>
            <Summary items={items} onOrderSubmit={handleOrderSubmit}/>
          </div>
        </div>
      </div>
  );
}

export default App;
