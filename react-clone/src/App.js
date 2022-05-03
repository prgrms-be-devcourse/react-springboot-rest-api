import './App.css'
import 'bootstrap/dist/css/bootstrap.css'
import React, {useEffect, useState} from 'react'
import {ProductList} from "./components/ProductList";
import {Summary} from "./components/Summary";
import axios from "axios";

function App() {
    // {'상태에 접근할 수 있는 변수명','상태값 바구는 함수'}
    const [products, setProducts] = useState([
        {productId: 'uuid-1', productName: '콜롬비아 커피1', category: '커피빈', price: '3000'},
        {productId: 'uuid-2', productName: '콜롬비아 커피2', category: '커피빈', price: '5000'},
        {productId: 'uuid-3', productName: '콜롬비아 커피3', category: '커피빈', price: '5500'}
    ]);

    const [items, setItems] = useState([]);

    const handleAddClicked = productId => {
        console.log(products.find(v => v.productId === productId));
        const product = products.find(v => v.productId === productId);
        const found = items.find(v => v.productId === productId);

        const updatedItems = found ? items.map(v => (v.productId === productId) ? {
            ...v,
            count: v.count + 1
        } : v) : [...items, {...product, count: 1}]
        setItems(updatedItems);
        console.log(productId, "clicked !")
    }// 랜더링과 리액트 컴포넌트 호출에서 갭이 생김
    useEffect(() => {
        // 비동기 작업은 모두 여기서
        axios.get("http://localhost:8080/api/v1/products")
            .then(v => setProducts(v.data));
    }, []);

    const handleOrderSubmit = (order) => {
        console.log("submit!")
        if (items.length === 0) {
            alert("아이템을 추가해 주세요");
            return;
        }
        axios.post("http://localhost:8080/api/v1/orders", {
            email: order.email,
            address: order.address,
            postcode: order.postcode,
            orderItems:
                items.map(v => ({
                    productId: v.productId,
                    category: v.category,
                    price: v.price,
                    quantity: v.count
                }))
        }).then(v => alert("주문 처리 완료!"),e => {
            alert("서버에 문제가 생겼습니다.");
            console.log(e);
        })
        console.log(order, items);
    };
    return (
        <div className="container-fluid">
            <div className="row justify-content-center m-4">
                <h1 className="text-center">Grids & Circle</h1>
            </div>
            <div className="card">
                <div className="row">
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <ProductList products={products} onAddClick={handleAddClicked}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <Summary items={items} onOrderSubmit={handleOrderSubmit}/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
