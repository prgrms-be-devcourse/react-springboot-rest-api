import React from "react";
export function SummaryItem({name, count, onRemove}) {

    return (
        <div className="row">
            <h6 className="p-0">{name}
                <span className="badge bg-dark text-">{count}개</span>
                <button type="button" className="btn-close btn-close" aria-label="Close"
                        onClick={() => onRemove(name)}/>
            </h6>
        </div>
    )
}